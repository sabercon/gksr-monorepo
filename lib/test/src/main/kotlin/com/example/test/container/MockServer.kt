package com.example.test.container

import com.example.util.toJson
import org.mockserver.client.ForwardChainExpectation
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType
import org.mockserver.model.RequestDefinition
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.testcontainers.containers.MockServerContainer
import org.testcontainers.utility.DockerImageName
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object MockServer {

    val CONTAINER = MockServerContainer(DockerImageName.parse("jamesdbloom/mockserver:mockserver-5.11.2"))
        .apply { start() }

    val CLIENT: MockServerClient = MockServerClient(CONTAINER.host, CONTAINER.serverPort)

    fun endpoint(): String {
        return CONTAINER.endpoint
    }

    fun reset() {
        CLIENT.reset()
    }

    fun mock(request: RequestDefinition): ForwardChainExpectation {
        return CLIENT.`when`(request)
    }
}

fun request(method: HttpMethod = HttpMethod.GET, path: String = ""): HttpRequest {
    return HttpRequest.request()
        .withMethod(method.name())
        .withPath(path)
}

fun response(
    status: HttpStatus = HttpStatus.OK,
    type: MediaType = MediaType.APPLICATION_JSON_UTF_8,
    body: String? = null,
): HttpResponse {
    return HttpResponse.response()
        .withStatusCode(status.value())
        .withReasonPhrase(status.reasonPhrase)
        .withBody(body, type)
}

fun jsonResponse(status: HttpStatus = HttpStatus.OK, body: Any): HttpResponse {
    return response(status = status, body = body.toJson())
}

/**
 * Reads a file from [path] and creates a [HttpResponse] with it.
 *
 * [path] can be specified with or without a leading slash, like `/foo/bar` or `foo/bar`.
 */
fun fileResponse(type: MediaType = MediaType.APPLICATION_JSON_UTF_8, path: String): HttpResponse {
    val body = Files.readString(ClassPathResource(path).file.toPath(), StandardCharsets.UTF_8)
    return response(type = type, body = body)
}
