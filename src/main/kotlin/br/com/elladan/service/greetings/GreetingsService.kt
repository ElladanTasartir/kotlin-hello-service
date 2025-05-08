package br.com.elladan.service.greetings

interface GreetingsService {
    fun addGreetings(greeting: Greeting)
    fun getGreetings(): List<Greeting>
    fun getRandomGreeting(): Greeting
}