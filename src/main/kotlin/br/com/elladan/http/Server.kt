package br.com.elladan.http

import br.com.elladan.service.hello.HelloService
import com.google.inject.Injector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object Server {
    fun start(injector: Injector) {
        val helloService = injector.getInstance(HelloService::class.java)

        embeddedServer(Netty, port = 8080){
            configureRouting(helloService)
        }.start(wait = true)
    }
}