package com.example.web3

import com.example.test.container.MockServer
import com.example.test.container.jsonResponse
import com.example.test.container.request
import com.example.test.container.response
import com.example.util.toJsonObject
import com.fasterxml.jackson.databind.JsonNode
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

fun mockJsonRpc(method: String, f: (params: List<JsonNode>) -> Any) {
    MockServer.mock(request(HttpMethod.POST)).respond {
        val req = it.bodyAsString.toJsonObject<JsonRpcRawRequest>()
        try {
            require(req.method == method)
            require(req.jsonrpc == "2.0")
            req.buildResponse(result = f(req.params))
        } catch (e: JsonRpcException) {
            val (code, message) = e.message!!.split(": ", limit = 2)
            req.buildResponse(error = JsonRpcResponse.Error(code.toInt(), message))
        } catch (e: Exception) {
            response(HttpStatus.BAD_REQUEST, MediaType.TEXT_PLAIN, e.message)
        }
    }
}

private data class JsonRpcRawRequest(
    val method: String,
    val params: List<JsonNode>,
    val id: String,
    val jsonrpc: String,
) {
    fun buildResponse(result: Any? = null, error: JsonRpcResponse.Error? = null): HttpResponse {
        return jsonResponse(body = JsonRpcResponse(result, error, id, jsonrpc))
    }
}
