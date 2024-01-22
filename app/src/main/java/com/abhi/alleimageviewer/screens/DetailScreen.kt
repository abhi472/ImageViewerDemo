package com.abhi.alleimageviewer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abhi.alleimageviewer.common.MainViewModel
import com.abhi.alleimageviewer.R
import com.abhi.alleimageviewer.ui.theme.AlleImageViewerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    navController: NavController,
    modalSheetState: ModalBottomSheetState
) {

    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.uiState.collectAsState().value
    val mlState = viewModel.mlState.collectAsState().value


    AlleImageViewerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Column(Modifier.height(400.dp)) {
                    ImageView(state = state)
                }
                Column() {
                    TextFieldExample()

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                stringResource(id = R.string.collections),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                            Text(stringResource(id = R.string.edit),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 18.dp)
                                    .clickable {

                                        coroutineScope.launch {
                                            if (modalSheetState.isVisible)
                                                modalSheetState.hide()
                                            else
                                                modalSheetState.show()
                                        }

                                    })

                        }
                    }
                    LazyRow(Modifier.padding(8.dp)) {
                        itemsIndexed(mlState.labelList) { index,item ->
                            InputChip(
                                selected = true,
                                onClick = {
                                },
                                label = {
                                    Text(item.text,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                                }
                            )

                        }
                    }

                    Text(stringResource(id = R.string.Description),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp))

                    Text(mlState.text,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp, modifier = Modifier.padding(10.dp))


                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldExample() {
    var textInput by remember { mutableStateOf("")}
    OutlinedTextField(value = textInput, onValueChange = {textInput = it},
        label = { Text(stringResource(id = R.string.add_a_note))},
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
}



