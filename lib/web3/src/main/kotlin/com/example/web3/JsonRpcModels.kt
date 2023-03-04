package com.example.web3

data class JsonRpcRequest(
    val method: String,
    val params: List<Any>,
    val id: String = "",
    val jsonrpc: String = "2.0",
)

data class JsonRpcResponse<out T : Any>(
    val result: T?,
    val error: Error?,
    val id: String,
    val jsonrpc: String,
) {
    data class Error(val code: Int, val message: String)
}
