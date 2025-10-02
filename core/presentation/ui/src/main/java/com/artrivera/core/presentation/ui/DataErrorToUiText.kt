package com.artrivera.core.presentation.ui

import com.artrivera.core.domain.utils.DataError


fun DataError.toText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.error_too_many_requests)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
        DataError.Network.INTERNAL_SERVER_ERROR -> UiText.StringResource(R.string.error_internal_server_error)
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(R.string.error_payload_too_large)
        DataError.Network.PARSING_ERROR -> UiText.StringResource(R.string.error_parsing_error)
        else -> UiText.StringResource(R.string.error_unknown)
    }
}