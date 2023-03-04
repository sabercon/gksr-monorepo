package com.example.web3.eth.contract

import com.example.web3.eth.EthFunction
import org.web3j.abi.datatypes.Address

object ERC173 {

    const val INTERFACE_ID = "0x7f5828d0"

    fun owner(): EthFunction<String> {
        return EthFunction.of(
            name = "owner",
            params = listOf(),
            resultType = Address::class,
        )
    }
}
