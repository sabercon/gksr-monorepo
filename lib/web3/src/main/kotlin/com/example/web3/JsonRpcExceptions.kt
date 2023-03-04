package com.example.web3

open class JsonRpcException(code: Int, message: String) : RuntimeException("$code: $message")

class MalformedResponseException(message: String = "Malformed response") : JsonRpcException(-1, message)
