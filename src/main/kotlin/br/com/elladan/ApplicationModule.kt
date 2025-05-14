package br.com.elladan

import br.com.elladan.http.Routing
import br.com.elladan.http.Server
import br.com.elladan.service.greetings.GreetingsService
import br.com.elladan.service.greetings.GreetingsServiceImpl
import br.com.elladan.service.hello.HelloService
import br.com.elladan.service.hello.HelloServiceImpl
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton

class ApplicationModule : AbstractModule() {
    @Provides
    @Singleton
    fun providesServer(routing: Routing): Server {
        return Server(routing)
    }

    @Provides
    @Singleton
    fun providesRouting(greetingsService: GreetingsService, helloService: HelloService): Routing {
        return Routing(helloService, greetingsService)
    }

    @Provides
    @Singleton
    fun providesHelloService(): HelloService {
        return HelloServiceImpl()
    }

    @Provides
    @Singleton
    fun providesGreetingsService(): GreetingsService {
        return GreetingsServiceImpl()
    }
}