package com.artrivera.core.domain

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)