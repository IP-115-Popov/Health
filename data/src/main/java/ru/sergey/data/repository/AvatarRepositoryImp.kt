package ru.sergey.data.repository

import android.content.Context
import ru.sergey.domain.task.repository.AvatarRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class AvatarRepositoryImp(private val context: Context) : AvatarRepository {

    private val fileName = "profile_image.jpg"

    override fun save(byteArray: ByteArray) {
        try {
            val file = File(context.filesDir, fileName)
            FileOutputStream(file).use { outputStream ->
                outputStream.write(byteArray)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun get(): ByteArray {
        return try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                FileInputStream(file).use { inputStream ->
                    val byteArray = ByteArray(file.length().toInt())
                    inputStream.read(byteArray)
                    byteArray
                }
            } else {
                ByteArray(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            ByteArray(0)
        }
    }
}
