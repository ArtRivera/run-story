package com.artrivera.auth.domain

import com.artrivera.core.domain.utils.DataError
import com.artrivera.core.domain.utils.EmptyResult

interface AuthRepository {

    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>

}