package br.com.elladan.http.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String)