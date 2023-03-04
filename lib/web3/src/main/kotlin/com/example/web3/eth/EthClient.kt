package com.example.web3.eth

import com.example.util.logger
import com.example.web3.JsonRpcException
import com.example.web3.JsonRpcRequest
import com.example.web3.JsonRpcResponse
import com.example.web3.MalformedResponseException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.ResolvableType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.util.retry.Retry
import java.time.Duration

/**
 * Client to interact with the JSON-RPC API of Ethereum nodes.
 *
 * @param endpoint Endpoint of the EVM node
 */
class EthClient(client: WebClient, endpoint: String) {

    private val log = logger()

    private val client = client.mutate().baseUrl(endpoint).build()

    private val retrySpec = Retry.backoff(3, Duration.ofSeconds(2))
        .filter { it !is WebClientResponseException }
        .doBeforeRetry { log.warn("Error on EVM JSON-RPC API {}: {}", endpoint, it.failure().message) }

    suspend fun <T : Any> call(address: String, function: EthFunction<T>): T {
        val params = listOf(
            mapOf("to" to address, "data" to function.signature()),
            BlockParameter.LATEST,
        )

        return callJsonRpc<String>(JsonRpcRequest("eth_call", params, address))
            .let { function.parse(it) }
    }

    /**
     * The use of `Reified Type Parameters` in this function may be too complicated.
     * Consider to simplify this in the future.
     */
    private suspend inline fun <reified T : Any> callJsonRpc(request: JsonRpcRequest): T {
        return client.post()
            .bodyValue(request)
            .retrieve()
            .bodyToMono(buildResponseType<T>())
            .retryWhen(retrySpec)
            .map { parseResponse(it) }
            .awaitSingle()
    }

    private inline fun <reified T : Any> buildResponseType(): ParameterizedTypeReference<JsonRpcResponse<T>> {
        val resultType = ResolvableType.forType(object : ParameterizedTypeReference<T>() {})
        val type = ResolvableType.forClassWithGenerics(JsonRpcResponse::class.java, resultType).type
        return ParameterizedTypeReference.forType(type)
    }

    private fun <T : Any> parseResponse(response: JsonRpcResponse<T>): T {
        return when {
            response.error != null -> throw JsonRpcException(response.error.code, response.error.message)
            response.result != null -> response.result
            else -> throw MalformedResponseException("Result is null")
        }
    }
}
