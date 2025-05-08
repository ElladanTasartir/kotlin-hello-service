package br.com.elladan.http.model

import br.com.elladan.service.greetings.Greeting
import kotlinx.serialization.Serializable

@Serializable
data class GreetingsResponse(val greetings: List<Greeting>)
