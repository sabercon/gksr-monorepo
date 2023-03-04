package com.example.web3.eth.contract

import com.example.web3.eth.EthFunction
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

object ERC721Enumerable {

    const val INTERFACE_ID = "0x780e9d63"

    fun totalSupply(): EthFunction<BigInteger> {
        return EthFunction.of(
            name = "totalSupply",
            params = listOf(),
            resultType = Uint256::class,
        )
    }

    fun tokenOfOwnerByIndex(owner: String, index: BigInteger): EthFunction<BigInteger> {
        return EthFunction.of(
            name = "tokenOfOwnerByIndex",
            params = listOf(Address(owner), Uint256(index)),
            resultType = Uint256::class,
        )
    }

    fun tokenByIndex(index: BigInteger): EthFunction<BigInteger> {
        return EthFunction.of(
            name = "tokenByIndex",
            params = listOf(Uint256(index)),
            resultType = Uint256::class,
        )
    }
}
