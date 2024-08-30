package com.jax.drawerapp.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun DrawCanvas(){
    val pathData = remember {
        mutableStateOf(PathData())
    }
    val pathList = remember {
        mutableStateListOf(PathData())
    }
    Column {
        Drawer(
            pathData = pathData,
            pathList = pathList
        )
        BottomPanel(
            onColorChange = {
                pathData.value = pathData.value.copy(color = it)
            },
            onBrushWidthChange = {
                pathData.value = pathData.value.copy(brushWidth = it)
            },
            onClearClick = {
                pathList.removeIf{path->
                    pathList[pathList.size-1]== path
                }
            },
            onCapClick = {
                pathData.value = pathData.value.copy(cap = it)
            }
        )
    }
}
@Composable
private fun Drawer(pathData: MutableState<PathData>, pathList: SnapshotStateList<PathData>) {
    var tempPath = Path()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.70f)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        pathList.add(
                            pathData.value.copy(
                                path = tempPath
                            )
                        )
                        tempPath = Path()
                    },
                    onDragEnd = {
                        pathList.add(
                            pathData.value.copy(
                                path = tempPath
                            )
                        )
                    }
                ) { change, dragAmount ->
                    tempPath.moveTo(
                        change.position.x - dragAmount.x,
                        change.position.y - dragAmount.y
                    )
                    tempPath.lineTo(
                        change.position.x,
                        change.position.y
                    )

                    if(pathList.size > 0){
                        pathList.removeAt(pathList.size - 1)
                    }

                    pathList.add(
                        pathData.value.copy(
                            path = tempPath
                        )
                    )
                }
            }
    ){
        pathList.forEach { pathData ->
            drawPath(
                pathData.path,
                color = pathData.color,
                style = Stroke(
                    width = pathData.brushWidth,
                    cap = pathData.cap
                )
            )
        }
    }
}