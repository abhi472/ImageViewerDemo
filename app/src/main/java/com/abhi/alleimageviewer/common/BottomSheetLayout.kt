package com.abhi.alleimageviewer.common

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
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
import com.abhi.alleimageviewer.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetLayout(viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue =  ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val mlState = viewModel.mlState.collectAsState().value


    var isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val modifier = if (isSheetFullScreen)
        Modifier
            .fillMaxSize()
    else
        Modifier.fillMaxWidth()

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    stringResource(id = R.string.collections),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyRow(Modifier.padding(8.dp)) {
                    itemsIndexed(mlState.labelList) { index,item ->
                        InputChip(
                            selected = true,
                            onClick = {
                            },
                            label = {
                                Text("${item.text}  X",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 14.sp, modifier = Modifier.padding(10.dp)
                                )
                            }
                        )

                    }
                }

                Text(
                    stringResource(id = R.string.sel),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyRow(Modifier.padding(8.dp)) {
                    itemsIndexed(viewModel.getMockList()) { index,item ->
                        InputChip(
                            selected = true,
                            onClick = {
                            },
                            label = {
                                Text("$item  +",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp, modifier = Modifier.padding(10.dp)
                                )
                            }
                        )

                    }
                }

                Button(onClick = {
                    coroutineScope.launch {
                        if (modalSheetState.isVisible)
                            modalSheetState.hide()
                        else
                            modalSheetState.show()
                    }
                },
                    Modifier.padding(horizontal = 36.dp)) {
                    Text(text = stringResource(id = R.string.done), color = Color.White)
                }


            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FeatureThatRequiresStoragePermission(viewModel, modalSheetState)

        }
    }
}