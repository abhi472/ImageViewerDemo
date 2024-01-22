package com.abhi.alleimageviewer

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.alleimageviewer.usecase.ImageListUseCase
import com.abhi.alleimageviewer.usecase.ImageMlUseCase
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.text.Text
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageListUseCase: ImageListUseCase,
    private val imageMlUseCase: ImageMlUseCase

): AndroidViewModel(AlleImageViewerApp()) {


    private val _uiState = MutableStateFlow(MediaState())
    val uiState: StateFlow<MediaState> = _uiState.asStateFlow()

    private val _mlState = MutableStateFlow(MLState())
    val mlState: StateFlow<MLState> = _mlState.asStateFlow()


    init {
        loadData()
    }

    fun loadData() {


        viewModelScope.launch {
            imageListUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .catch {
                    updateUiState(isError = true, isLoading = false)

                }
                .collect {
                    updateUiState(files = it,
                        isError = false,
                        isLoading = false)

                }
        }
    }

    fun loadLabels(file: File, context: Context) {


        viewModelScope.launch {
            imageMlUseCase.invokeLabel(file, context)
                .flowOn(Dispatchers.IO)
                .catch {
                    updateMlState(emptyList())

                }
                .collectLatest {
                    it.addOnSuccessListener {it ->
                        updateMlState(it)

                    }.addOnFailureListener {

                    }

                }
        }
    }

    fun loadOcr(file: File, context: Context) {


        viewModelScope.launch {
            imageMlUseCase.invokeOcr(file, context)
                .flowOn(Dispatchers.IO)
                .catch {
                    updateMlState(emptyList())

                }
                .collectLatest {
                    it.addOnSuccessListener {it ->
                        updateMlState(text = it.text)

                    }.addOnFailureListener {

                    }

                }
        }
    }



    fun updateUiState(
         files: List<File> = uiState.value.files,
         isError: Boolean = uiState.value.isError,
         isLoading: Boolean = uiState.value.isLoading,
         selectedFile: Int = uiState.value.selectedFile,
         labelList: List<ImageLabel> = uiState.value.labelList
    ) {
        _uiState.value = uiState.value.copy(
            files = files,
            isError = isError,
            isLoading = isLoading,
            selectedFile = selectedFile,
            labelList = labelList
        )
    }

    fun updateMlState(
        labelList: List<ImageLabel> = mlState.value.labelList,
        text:String = mlState.value.text
    ) {
        _mlState.value = mlState.value.copy(
            labelList = labelList,
            text = text
        )
    }




}