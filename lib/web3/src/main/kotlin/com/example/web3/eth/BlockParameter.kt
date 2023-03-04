package com.example.web3.eth

import org.web3j.utils.Numeric

/**
 * See [here](https://eth.wiki/json-rpc/API#the-default-block-parameter).
 */
@JvmInline
value class BlockParameter private constructor(val value: String) {

    companion object {

        val LATEST = BlockParameter("latest")

        val EARLIEST = BlockParameter("earliest")

        val PENDING = BlockParameter("pending")

        fun of(number: Long) = BlockParameter(Numeric.encodeQuantity(number.toBigInteger()))
    }
}
