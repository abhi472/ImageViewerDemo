package com.abhi.alleimageviewer.usecase

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.abhi.alleimageviewer.repository.ImageListRepository
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class ImageMlUseCase @Inject constructor(private val imageListRepository: ImageListRepository) {

    suspend fun invokeLabel(file: File, context:Context) = flow {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(context, file.toUri())
            emit(imageListRepository.getLabeler().process(image))
        } catch (_: Exception) {
        }
    }

    suspend fun invokeOcr(file: File, context:Context) = flow {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(context, file.toUri())
            emit(imageListRepository.getRecognizer().process(image))
        } catch (_: Exception) {
        }
    }


}