package br.com.elladan.service.greetings

import com.google.inject.util.Modules.override

class GreetingsServiceImpl : GreetingsService {
    private val greetings = mutableListOf<Greeting>()

    override fun addGreetings(greeting: Greeting) {
        greetings.add(greeting)
    }

    override fun getGreetings(): List<Greeting> {
        return greetings
    }

    override fun getRandomGreeting(): Greeting {
        if (greetings.isEmpty()) {
            return Greeting("Hello! No Greetings yet!")
        }

        return greetings.random()
    }
}