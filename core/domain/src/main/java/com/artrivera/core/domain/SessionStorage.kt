package com.artrivera.core.domain

interface SessionStorage {

    suspend fun get(): Session?

    suspend fun set(info: Session)

    suspend fun clear()
}