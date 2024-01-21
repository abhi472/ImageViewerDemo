package com.abhi.alleimageviewer.repository

import java.io.File

interface ImageListRepository {

    fun getAllImages(): List<File>
}