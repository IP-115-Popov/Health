package ru.sergey.health.feature.profile.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sergey.domain.achievement.usecase.GetAchievementsUseCase
import ru.sergey.domain.profile.models.Player
import ru.sergey.domain.profile.usecase.GetAvatarUseCase
import ru.sergey.domain.profile.usecase.GetProfileUseCase
import ru.sergey.domain.profile.usecase.SaveAvatarUseCase
import ru.sergey.domain.profile.usecase.SaveProfileUseCase
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState(Player()))
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getProfileUseCase.execute().onEach { player: Player ->
                _state.update { it ->
                    it.copy(player = player)
                }
            }.launchIn(viewModelScope)
        }
        loadAvatar()
        collectAchievement()
    }

    private fun collectAchievement() {
        viewModelScope.launch(Dispatchers.IO) {
            getAchievementsUseCase().collect {
                var exp = 0
                it.forEach {
                    if (it.isUnlocked) exp += it.exp
                }
                val level = exp / 10
                withContext(Dispatchers.Main.immediate) {
                    _state.update {
                        it.copy(
                            player = it.player.copy(
                                ex = exp,
                                level = level
                            )
                        )
                    }
                }
                savePlayer()
            }
        }
    }

    fun savePlayer() = viewModelScope.launch(Dispatchers.IO) {
            saveProfileUseCase.execute(state.value.player)
        }


    fun setName(value: String) {
        _state.update {
            it.copy(player = it.player.copy(name = value))
        }
    }

    fun setAvatar(value: ImageBitmap) {
        _state.update {
            it.copy(imgAvatar = value)
        }
        saveAvatar()
        loadAvatar()
    }

    fun saveAvatar() {
        state.value.imgAvatar?.let { data: ImageBitmap ->
            val byteArray = imageBitmapToByteArray(data)

            saveAvatarUseCase.execute(byteArray)
        }
    }

    fun loadAvatar() {
        val byteArray = getAvatarUseCase.execute()
        val data = byteArrayToImageBitmap(byteArray)

        _state.update {
            it.copy(imgAvatar = data)
        }
    }

    // Преобразуем ImageBitmap в байтовый массив
    private fun imageBitmapToByteArray(imageBitmap: ImageBitmap): ByteArray {
        val bitmap: Bitmap = imageBitmap.asAndroidBitmap()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    // Преобразуем байтовый массив обратно в ImageBitmap
    private fun byteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap? {
        if (byteArray.isEmpty()) return null

        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap.asImageBitmap()
    }

    // Преобразуем URI в ImageBitmap
    fun uriToImageBitmap(context: Context, uri: Uri): ImageBitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        return bitmap.asImageBitmap()
    }

    override fun onCleared() {
        savePlayer()
    }
}