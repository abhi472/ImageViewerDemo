package com.abhi.alleimageviewer.repository

import android.os.Environment
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
                if (currentFile.name.endsWith(".jpeg")
                    ||currentFile.name.endsWith(".jpeg")) {
                    fileList.add(currentFile.absoluteFile)
                }
            }
        }
        return fileList
    }
}