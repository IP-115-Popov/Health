package ru.sergey.data.repository

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.sergey.data.storage.PlayerStorage
import ru.sergey.data.storage.PlayerStorageSerializer
import ru.sergey.domain.models.Player
import ru.sergey.domain.repository.ProfileRepository

private val Context.protoDataStore by dataStore("Profile.json", serializer = PlayerStorageSerializer)

class ProfileRepositoryImp(val context: Context): ProfileRepository {

    override suspend fun save(player:Player) {
        context.protoDataStore.updateData {
            PlayerStorage.fromPlayer(player)
        }
    }

    override suspend fun get() : Flow<Player> {
        return context.protoDataStore.data.map { it.toPlayer()}
    }
}