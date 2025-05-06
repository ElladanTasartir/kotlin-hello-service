package br.com.elladan

import br.com.elladan.http.Server
import com.google.inject.Guice

fun main() {
    val injector = Guice.createInjector(ApplicationModule())
    Server.start(injector)
}
