package com.artrivera.auth.domain

interface PatternValidator {

    fun matches(value: String): Boolean
}