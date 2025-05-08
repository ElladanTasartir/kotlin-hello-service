package br.com.elladan.http

import br.com.elladan.http.model.ErrorResponse
import br.com.elladan.http.model.GreetingsResponse
import br.com.elladan.http.model.HelloResponse
import br.com.elladan.service.greetings.Greeting
import br.com.elladan.service.greetings.GreetingsService
import br.com.elladan.service.hello.HelloService
import com.google.inject.Injector
import io.ktor.http.ContentType.Message.Http
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond

object Server {
    fun start(injector: Injector) {
        embeddedServer(Netty, port = 8080){
            install(ContentNegotiation) {
                json()
            }

            install(StatusPages)  {
                status(HttpStatusCode.NotFound) {
                    handleNotFound(call)
                }

                exception<Throwable> {
                    call, throwable -> call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Internal Server Error"))
                }
            }

            configureRouting(this@Server, injector)
        }.start(wait = true)
    }

    suspend fun getHello(call: ApplicationCall, helloService: HelloService) {
        val name = call.request.queryParameters["name"] ?: "World"
        val message = helloService.sayHello(name)
        call.respond(HelloResponse(message))
    }

    suspend fun getGreetings(call: ApplicationCall, greetingsService: GreetingsService) {
        val greetings = greetingsService.getGreetings()
        call.respond(GreetingsResponse(greetings))
    }

    suspend fun getGreeting(call: ApplicationCall, greetingsService: GreetingsService) {
        val greeting = greetingsService.getRandomGreeting()
        call.respond(greeting)
    }

    suspend fun addGreeting(call: ApplicationCall, greetingsService: GreetingsService) {
        val result = runCatching {
            val greeting = call.receive<Greeting>()
            greetingsService.addGreetings(greeting)
            greeting
        }

        result.onSuccess { greeting -> call.respond(HttpStatusCode.Created, greeting) }
        result.onFailure { call.respond(HttpStatusCode.BadRequest, ErrorResponse("Malformed Greeting")) }
    }

    suspend fun handleNotFound(call: ApplicationCall) {
        call.respond(HttpStatusCode.NotFound, ErrorResponse("Resource not found"))
    }
}