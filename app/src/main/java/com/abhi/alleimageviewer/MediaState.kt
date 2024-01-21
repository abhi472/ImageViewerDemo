package com.abhi.alleimageviewer

import android.graphics.Bitmap
import java.io.File

data class MediaState (

    val files: List<File> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)