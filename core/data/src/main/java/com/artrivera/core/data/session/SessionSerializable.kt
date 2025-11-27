package com.artrivera.core.data.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionSerializable(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)