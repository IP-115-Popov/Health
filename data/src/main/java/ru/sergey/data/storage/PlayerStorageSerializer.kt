package ru.sergey.data.storage

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object PlayerStorageSerializer : Serializer<PlayerStorage> {
    override val defaultValue: PlayerStorage = PlayerStorage()

    override suspend fun readFrom(input: InputStream): PlayerStorage {
        return try{
            Json.decodeFromString(
                deserializer = PlayerStorage.serializer(),
                string = input.readBytes().toString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            PlayerStorage()
        }
    }

    override suspend fun writeTo(t: PlayerStorage, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = PlayerStorage.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}