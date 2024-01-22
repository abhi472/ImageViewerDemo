package com.abhi.alleimageviewer.repository

import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.text.TextRecognizer
import java.io.File

interface ImageListRepository {

    fun getAllImages(): List<File>
    fun getLabeler(): ImageLabeler

    fun getRecognizer(): TextRecognizer
}