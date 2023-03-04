package com.example.web3.eth.contract

import com.example.web3.eth.EthFunction
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Bytes4
import org.web3j.utils.Numeric

object ERC165 {

    fun supportsInterface(interfaceId: String): EthFunction<Boolean> {
        return EthFunction.of(
            name = "supportsInterface",
            params = listOf(Bytes4(Numeric.hexStringToByteArray(interfaceId))),
            resultType = Bool::class,
        )
    }
}
