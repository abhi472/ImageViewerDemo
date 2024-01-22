package com.abhi.alleimageviewer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.abhi.alleimageviewer.common.MainViewModel
import com.abhi.alleimageviewer.states.MediaState
import com.abhi.alleimageviewer.ui.theme.AlleImageViewerTheme
import java.io.File

@Composable
fun PictureScreen(viewModel: MainViewModel,
                  navController: NavController) {

    val state = viewModel.uiState.collectAsState().value
    AlleImageViewerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Column(Modifier.weight(10f)) {
                    ImageView(state = state)

                }
                Column(Modifier.weight(1f)) {
                    HorizontalScrollView(state = state, viewModel)

                }
            }
        }
    }
}


    @Composable
    fun ImageView(state: MediaState) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            state.files.let {
                if (it.isNotEmpty()) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.files[state.selectedFile])
                            .build(),
                        contentDescription = "icon",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }

    @Composable
    fun HorizontalScrollView(state: MediaState, viewModel: MainViewModel) {

        LazyRow(modifier = Modifier.fillMaxWidth()) {

            itemsIndexed(state.files) { index, item ->
                ImageItem(state = state, index = index, item, viewModel)

            }
        }

    }

    @Composable
    fun ImageItem(
        state: MediaState,
        index: Int,
        item: File,
        viewModel: MainViewModel
    ) {

        Box(
            modifier = Modifier
                .width(45.dp)
                .height(100.dp)
                .padding(1.dp)
                .background(color = if (state.selectedFile == index) Color.White else Color.Black)


        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item)
                    .build(),
                contentDescription = "icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable {
                        viewModel.updateUiState(selectedFile = index)
                    }

            )

        }
    }