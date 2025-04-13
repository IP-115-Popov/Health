package ru.sergey.data.profile.serializer

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sergey.data.profile.model.PlayerStorage
import java.io.InputStream
import java.io.OutputStream

object PlayerStorageSerializer : Serializer<PlayerStorage> {
    override val defaultValue: PlayerStorage = PlayerStorage()

    override suspend fun readFrom(input: InputStream): PlayerStorage {
        val t = input.readBytes().toString(Charsets.UTF_8)
        return try{
            Json.decodeFromString(
                deserializer = PlayerStorage.serializer(),
                string = t
            )
        } catch (e: Exception) {
            e.printStackTrace()
            PlayerStorage()
        }
    }

    override suspend fun writeTo(t: PlayerStorage, output: OutputStream) {
        val jsonString = Json.encodeToString(
            serializer = PlayerStorage.serializer(),
            value = t
        )
        val ByteArray = jsonString.toByteArray(Charsets.UTF_8)
        withContext(Dispatchers.IO) {
            output.write(
                ByteArray
            )
        }
    }
}