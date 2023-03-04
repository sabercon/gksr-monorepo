package com.example.web3.eth

import com.example.web3.MalformedResponseException
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import kotlin.reflect.KClass

class EthFunction<out T : Any> private constructor(
    private val function: Function,
    private val parseResult: (List<Type<*>>) -> T,
) {

    /**
     * Returns the function signature used for calling the contract method.
     */
    fun signature(): String {
        return FunctionEncoder.encode(function)
    }

    fun parse(response: String): T {
        return parseResult(FunctionReturnDecoder.decode(response, function.outputParameters))
    }

    companion object {

        /** For functions that return one type */
        fun <T : Any> of(
            name: String,
            params: List<Type<*>>,
            resultType: KClass<out Type<T>>,
        ): EthFunction<T> {
            val result = listOf(TypeReference.create(resultType.java))

            return EthFunction(Function(name, params, result)) {
                require(it.size == 1)
                it[0].value()
            }
        }

        /** For functions that return two types */
        fun <T1 : Any, T2 : Any> of(
            name: String,
            params: List<Type<*>>,
            resultTypes: Pair<KClass<out Type<T1>>, KClass<out Type<T2>>>,
        ): EthFunction<Pair<T1, T2>> {
            val results = resultTypes.toList().map { TypeReference.create(it.java) }

            return EthFunction(Function(name, params, results)) {
                require(it.size == 2)
                Pair(it[0].value(), it[1].value())
            }
        }

        private fun require(value: Boolean) {
            if (!value) throw MalformedResponseException()
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T> Type<*>.value(): T {
            return when (this) {
                // PostgreSQL doesn't support storing NULL (\0x00) characters in text fields.
                is Utf8String -> value.replace("\u0000", "")
                else -> value
            } as T
        }
    }
}
