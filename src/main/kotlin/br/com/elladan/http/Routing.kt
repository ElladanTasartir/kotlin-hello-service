package br.com.elladan.http

import br.com.elladan.service.hello.HelloService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting(helloService: HelloService) {
    routing {
       get("/") {
           val name = call.request.queryParameters["name"] ?: "World"
           call.respondText(helloService.sayHello(name))
       }
    }
}