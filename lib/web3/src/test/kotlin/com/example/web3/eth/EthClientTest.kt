package com.example.web3.eth

import com.example.test.container.MockServer
import com.example.web3.MalformedResponseException
import com.example.web3.mockJsonRpc
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.web.reactive.function.client.WebClient
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256

class EthClientTest : FunSpec({

    afterContainer { MockServer.reset() }

    val client = EthClient(WebClient.create(), MockServer.endpoint())

    context("eth_call") {
        val azuki = "0xed5af388653567af2f388e6224dc7c4b3241c544"
        val nameSignature = "0x06fdde03"
        val tokenUriSignature = "0xc87b56dd0000000000000000000000000000000000000000000000000000000000000001"

        mockJsonRpc("eth_call") { params ->
            require(params.size == 2)
            require(params[0]["to"].textValue() == azuki)
            require(params[1].textValue() == "latest")

            when (params[0]["data"].textValue()) {
                nameSignature ->
                    "0x00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000005417a756b69000000000000000000000000000000000000000000000000000000"

                tokenUriSignature ->
                    "0x0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000005368747470733a2f2f696b7a7474702e6d7970696e6174612e636c6f75642f697066732f516d51466b4c535179736a393473354776544850797a547872617777746a67696959533254424c677276773843572f3100000000000000000000000000"

                else -> "0x"
            }
        }

        test("Returns the name of the collection when calling `name()`") {
            val function = EthFunction.of(
                name = "name",
                params = listOf(),
                resultType = Utf8String::class,
            )
            val name = client.call(azuki, function)
            name shouldBe "Azuki"
        }

        test("Returns the URI of the token 1 when calling `tokenURI(uint256)` with `1`") {
            val function = EthFunction.of(
                name = "tokenURI",
                params = listOf(Uint256(1)),
                resultType = Utf8String::class,
            )
            val uri = client.call(azuki, function)
            uri shouldBe "https://ikzttp.mypinata.cloud/ipfs/QmQFkLSQysj94s5GvTHPyzTxrawwtjgiiYS2TBLgrvw8CW/1"
        }

        test("Return errors when the function is not supported") {
            val function = EthFunction.of(
                name = "hello",
                params = listOf(),
                resultType = Utf8String::class,
            )
            shouldThrow<MalformedResponseException> {
                client.call(azuki, function)
            }
        }
    }
})
