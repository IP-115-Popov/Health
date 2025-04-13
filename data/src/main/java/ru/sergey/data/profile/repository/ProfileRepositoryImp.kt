package ru.sergey.data.profile.repository

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.sergey.data.profile.model.PlayerStorage
import ru.sergey.data.profile.serializer.PlayerStorageSerializer
import ru.sergey.domain.profile.models.Player
import ru.sergey.domain.task.repository.ProfileRepository

private val Context.protoDataStore by dataStore(
    "Profile.json",
    serializer = PlayerStorageSerializer
)

class ProfileRepositoryImp(val context: Context) : ProfileRepository {

    override suspend fun save(player: Player) {
        context.protoDataStore.updateData {
            PlayerStorage.fromPlayer(player)
        }
    }

    override suspend fun get(): Flow<Player> {
        return context.protoDataStore.data.map { it.toPlayer() }
    }
}