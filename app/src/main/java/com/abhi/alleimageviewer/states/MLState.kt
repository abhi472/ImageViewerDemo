package com.abhi.alleimageviewer.states

import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.text.Text
import java.io.File

data class MLState (
    val labelList: List<ImageLabel> = emptyList(),
    val text: String = ""
)