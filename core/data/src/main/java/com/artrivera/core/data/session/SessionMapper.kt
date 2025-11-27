package com.artrivera.core.data.session

import com.artrivera.core.domain.Session


fun Session.toSessionSerializable(): SessionSerializable = SessionSerializable(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)


fun SessionSerializable.toSession(): Session = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)