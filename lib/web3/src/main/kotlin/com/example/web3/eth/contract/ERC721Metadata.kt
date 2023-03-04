package com.example.web3.eth.contract

import com.example.web3.eth.EthFunction
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

object ERC721Metadata {

    const val INTERFACE_ID = "0x5b5e139f"

    fun name(): EthFunction<String> {
        return EthFunction.of(
            name = "name",
            params = listOf(),
            resultType = Utf8String::class,
        )
    }

    fun symbol(): EthFunction<String> {
        return EthFunction.of(
            name = "symbol",
            params = listOf(),
            resultType = Utf8String::class,
        )
    }

    fun tokenURI(tokenId: BigInteger): EthFunction<String> {
        return EthFunction.of(
            name = "tokenURI",
            params = listOf(Uint256(tokenId)),
            resultType = Utf8String::class,
        )
    }
}
