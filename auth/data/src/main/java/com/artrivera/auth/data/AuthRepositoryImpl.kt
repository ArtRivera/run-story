package com.artrivera.auth.data

import com.artrivera.auth.domain.AuthRepository
import com.artrivera.core.data.post
import com.artrivera.core.domain.Session
import com.artrivera.core.domain.SessionStorage
import com.artrivera.core.domain.utils.DataError
import com.artrivera.core.domain.utils.EmptyResult
import com.artrivera.core.domain.utils.Result
import com.artrivera.core.domain.utils.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage,
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )

        if (result is Result.Success) {
            sessionStorage.set(
                Session(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId,
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(email = email, password = password)
        )
    }
}