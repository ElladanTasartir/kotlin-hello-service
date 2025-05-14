package br.com.elladan

import br.com.elladan.http.Server
import com.google.inject.Guice

fun main() {
    val injector = Guice.createInjector(ApplicationModule())
    val server = injector.getInstance(Server::class.java)
    server.start()
}
