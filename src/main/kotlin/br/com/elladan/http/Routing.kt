package br.com.elladan.http

import br.com.elladan.http.model.ErrorResponse
import br.com.elladan.http.model.GreetingsResponse
import br.com.elladan.http.model.HelloResponse
import br.com.elladan.service.greetings.Greeting
import br.com.elladan.service.greetings.GreetingsService
import br.com.elladan.service.hello.HelloService
import com.google.inject.Inject
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


class Routing @Inject constructor(
    private val helloService: HelloService,
    private val greetingsService: GreetingsService,
) {
    fun configureRouting(application: Application) {
        application.routing  {
            this.route("/hello") {
                get { getHello(call) }
            }

            this.route("/greetings") {
                get { getGreetings(call) }
                post { addGreeting(call) }
            }

            this.route("/greeting") {
                get { getGreeting(call) }
            }
        }
    }

    suspend fun getHello(call: ApplicationCall) {
        val name = call.request.queryParameters["name"] ?: "World"
        val message = helloService.sayHello(name)
        call.respond(HelloResponse(message))
    }

    suspend fun getGreetings(call: ApplicationCall) {
        val greetings = greetingsService.getGreetings()
        call.respond(GreetingsResponse(greetings))
    }

    suspend fun getGreeting(call: ApplicationCall) {
        val greeting = greetingsService.getRandomGreeting()
        call.respond(greeting)
    }

    suspend fun addGreeting(call: ApplicationCall) {
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