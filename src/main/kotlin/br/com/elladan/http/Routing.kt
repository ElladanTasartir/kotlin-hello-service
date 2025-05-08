package br.com.elladan.http

import br.com.elladan.service.greetings.GreetingsService
import br.com.elladan.service.hello.HelloService
import com.google.inject.Injector
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting(server: Server, injector: Injector) {
    val helloService = injector.getInstance(HelloService::class.java)
    val greetingsService = injector.getInstance(GreetingsService::class.java)

    routing {
       get("/hello") { server.getHello(call, helloService) }
    }

    routing {
        get("/greetings") { server.getGreetings(call, greetingsService) }
        get("/greeting") { server.getGreeting(call, greetingsService) }
        post("/greetings") { server.addGreeting(call, greetingsService) }
    }
}