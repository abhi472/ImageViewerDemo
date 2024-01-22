package com.abhi.alleimageviewer.usecase;

import com.abhi.alleimageviewer.repository.ImageListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class ImageListUseCase @Inject constructor(private val repository: ImageListRepository){

    suspend operator fun invoke() =  flow {
        emit(repository.getAllImages())
    }



}