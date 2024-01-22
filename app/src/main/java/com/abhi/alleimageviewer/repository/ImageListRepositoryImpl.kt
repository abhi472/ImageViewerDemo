package com.abhi.alleimageviewer.repository

import android.os.Environment
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import javax.inject.Inject

class ImageListRepositoryImpl @Inject constructor(): ImageListRepository {
    private val fullPath = File(
        Environment.getExternalStorageDirectory().absolutePath
            + File.separator
            + "Pictures"
            + File.separator
            + "Screenshots")
    override fun getAllImages(): List<File> {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = fullPath.listFiles()

        if (!listAllFiles.isNullOrEmpty()) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".png")
                    ||currentFile.name.endsWith(".jpeg")) {
                    fileList.add(currentFile.absoluteFile)
                }
            }
        }
        return fileList
    }

    override fun getLabeler(): ImageLabeler {
        val options = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.7f)
            .build()
        return ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
    }

    override fun getRecognizer(): TextRecognizer {
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    }

}