package com.artrivera.core.domain.utils

sealed interface DataError: Error {

    enum class Network: DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        NOT_FOUND,
        CONFLICT,
        NO_INTERNET,
        INTERNAL_SERVER_ERROR,
        PAYLOAD_TOO_LARGE,
        PARSING_ERROR,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
    }
}