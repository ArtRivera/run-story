package com.artrivera.core.data.session

import android.content.SharedPreferences
import com.artrivera.core.domain.Session
import com.artrivera.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(private val sharedPreferences: SharedPreferences) : SessionStorage {
    override suspend fun get(): Session? {
        return withContext(Dispatchers.IO){
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<SessionSerializable>(it).toSession()
            }
        }
    }

    override suspend fun set(info: Session) {
        withContext(Dispatchers.IO) {
            val json = Json.encodeToString(info.toSessionSerializable())
            sharedPreferences.edit(commit = true) { putString(KEY_AUTH_INFO, json) }
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit(commit = true) { remove(KEY_AUTH_INFO) }
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}