package com.abhi.alleimageviewer

import android.graphics.Bitmap
import com.google.mlkit.vision.label.ImageLabel
import java.io.File

data class MediaState (

    val files: List<File> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val selectedFile: Int = 0,
    val labelList: List<ImageLabel> = emptyList()
)