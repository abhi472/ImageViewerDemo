package com.abhi.alleimageviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.alleimageviewer.usecase.ImageListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val imageListUseCase: ImageListUseCase): ViewModel() {

    private val _uiState = MutableStateFlow(MediaState())
    val uiState: StateFlow<MediaState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
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


    private fun updateUiState(
         files: List<File> = uiState.value.files,
         isError: Boolean = uiState.value.isError,
         isLoading: Boolean = uiState.value.isLoading
    ) {
        _uiState.value = uiState.value.copy(
            files = files,
            isError = isError,
            isLoading = isLoading
        )
    }




}