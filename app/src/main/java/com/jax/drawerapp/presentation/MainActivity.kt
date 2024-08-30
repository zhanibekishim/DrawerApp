package com.jax.drawerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.jax.drawerapp.ui.theme.DrawerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawerAppTheme {
                DrawCanvas()
            }
        }
    }
}

@Composable
fun DrawCanvas() {
    val tempPath = Path()

    val path = remember {
        mutableStateOf(Path())
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures { change, _ ->
                    tempPath.moveTo(
                        change.previousPosition.x,
                        change.previousPosition.y
                    )
                    tempPath.lineTo(
                        change.position.x,
                        change.position.y
                    )

                    path.value = Path().apply {
                        addPath(tempPath)
                    }
                }
            }
    ){
        drawPath(
            path.value,
            brush = Brush.sweepGradient(listOf(Color.Yellow, Color.Red, Color.LightGray)),
            style = Stroke(5f)
        )
    }
}
