package br.com.elladan.http.middleware

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.util.pipeline.PipelineContext

object Logging {
    suspend fun intercept(
        context: PipelineContext<Unit, ApplicationCall>,
    ) {
        val startTime = System.currentTimeMillis()
        val call = context.call

        try {
            context.proceed()
        } finally {
            val duration = System.currentTimeMillis() - startTime
            val status = call.response.status()?.value ?: 0

            val level = when (status) {
                in 200..399 -> "SUCCESS"
                in 400..499 -> "ERROR"
                in 500..599 -> "INTERNAL ERROR"
                else -> "ERROR"
            }

            log(
                level = level,
                method = call.request.httpMethod.value,
                path = call.request.path(),
                statusCode = status,
                timeInMs = duration,
            )
        }
    }

    private fun log(level: String, method: String, path: String, statusCode: Int, timeInMs: Long) {
        println("[$level] $method $path: $statusCode - ${timeInMs}ms")
    }
}