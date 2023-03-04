package com.example.web3.eth.contract

import com.example.web3.eth.EthFunction
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

object ERC721 {

    const val INTERFACE_ID = "0x80ac58cd"

    fun balanceOf(owner: String): EthFunction<BigInteger> {
        return EthFunction.of(
            name = "balanceOf",
            params = listOf(Address(owner)),
            resultType = Uint256::class,
        )
    }

    fun ownerOf(tokenId: BigInteger): EthFunction<String> {
        return EthFunction.of(
            name = "ownerOf",
            params = listOf(Uint256(tokenId)),
            resultType = Address::class,
        )
    }

    fun getApproved(tokenId: BigInteger): EthFunction<String> {
        return EthFunction.of(
            name = "getApproved",
            params = listOf(Uint256(tokenId)),
            resultType = Address::class,
        )
    }

    fun isApprovedForAll(owner: String, operator: String): EthFunction<Boolean> {
        return EthFunction.of(
            name = "isApprovedForAll",
            params = listOf(Address(owner), Address(operator)),
            resultType = Bool::class,
        )
    }
}
