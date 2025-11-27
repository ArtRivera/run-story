package com.artriveram.auth.data

import com.artrivera.auth.domain.AuthRepository
import com.artrivera.core.data.post
import com.artrivera.core.domain.utils.DataError
import com.artrivera.core.domain.utils.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            request = RegisterRequest(email = email, password = password)
        )
    }
}