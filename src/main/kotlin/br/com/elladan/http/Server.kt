package br.com.elladan.http

import br.com.elladan.http.middleware.Logging
import br.com.elladan.http.model.ErrorResponse
import br.com.elladan.http.model.GreetingsResponse
import br.com.elladan.http.model.HelloResponse
import br.com.elladan.service.greetings.Greeting
import br.com.elladan.service.greetings.GreetingsService
import br.com.elladan.service.greetings.GreetingsServiceImpl
import br.com.elladan.service.hello.HelloService
import br.com.elladan.service.hello.HelloServiceImpl
import com.google.inject.Inject
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond

class Server @Inject constructor (
    private val routing: Routing,
) {
    fun start() {
        embeddedServer(Netty, port = 8080){
            install(ContentNegotiation) {
                json()
            }

            intercept(ApplicationCallPipeline.Monitoring) {
                Logging.intercept(this)
            }

            install(StatusPages)  {
                status(HttpStatusCode.NotFound) {
                    routing.handleNotFound(call)
                }

                exception<Throwable> {
                    call, throwable -> call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Internal Server Error"))
                }
            }

            routing.configureRouting(this)
        }.start(wait = true)
    }
}