package ru.sergey.health.presentation.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sergey.domain.UseCase.GetProfileUseCase
import ru.sergey.domain.UseCase.SaveProfileUseCase
import ru.sergey.domain.models.Player
import ru.sergey.health.model.PlayerUIModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getProfileUseCase: GetProfileUseCase, val saveProfileUseCase: SaveProfileUseCase
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
    }

    fun savePlayer() {
        viewModelScope.launch {
            saveProfileUseCase.execute(state.value.player)
        }
    }

    fun setName(value: String) {
        _state.update {
            it.copy(player = it.player.copy(name = value))
        }
    }

    fun setAvatar(value: String) {
        _state.update {
            it.copy(player = it.player.copy(avatar = value))
        }
    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        try {
            val resolver = context.contentResolver
            val inputStream: InputStream? = resolver.openInputStream(uri)

            inputStream?.let { input ->
                val imageFile = File(context.filesDir, "profile_image.jpg")
                val outputStream = FileOutputStream(imageFile)
                input.copyTo(outputStream)
                outputStream.flush()
                outputStream.close()
                return imageFile.absolutePath
            }
        } catch (e: Exception) {
            Log.e("ProfileScreen", "Error saving image", e)
        }
        return null
    }

    fun loadImageFromStorage(context: Context): ImageBitmap? {
        val imageFile = File(context.filesDir, "profile_image.jpg")
        if (imageFile.exists()) {
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            return bitmap?.asImageBitmap()
        }
        return null
    }

    override fun onCleared() {
        savePlayer()
    }
}