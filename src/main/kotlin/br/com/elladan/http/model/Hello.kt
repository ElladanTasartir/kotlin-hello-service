package br.com.elladan.http.model

import kotlinx.serialization.Serializable

@Serializable
data class HelloResponse(val message: String)