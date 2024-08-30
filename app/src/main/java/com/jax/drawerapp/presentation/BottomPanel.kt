package com.jax.drawerapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jax.drawerapp.R

@Composable
fun BottomPanel(
    onColorChange: (Color) -> Unit,
    onBrushWidthChange: (Float) -> Unit,
    onClearClick: () -> Unit,
    onCapClick: (StrokeCap) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorList { color ->
            onColorChange(color)
        }
        CustomSlider { width ->
            onBrushWidthChange(width)
        }
        ButtonPanel(
            onClearClick = {
                onClearClick()
            },
            onCapClick = { cap ->
                onCapClick(cap)
            }
        )
    }
}

@Composable
fun ColorList(onColorChange: (Color) -> Unit) {
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Black,
        Color.Magenta,
        Color.Yellow,
        Color.Green,
    )

    LazyRow(
        modifier = Modifier.padding(10.dp)
    ) {
        items(colors) { color ->
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { onColorChange(color) }
                    .size(40.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

@Composable
fun CustomSlider(
    onBrushWidthChange: (Float) -> Unit
) {
    var sliderPosition by remember {
        mutableFloatStateOf(0.05f)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Width of brush = ${(sliderPosition * 100).toInt()}")
        Slider(
            value = sliderPosition,
            onValueChange = { newPosition ->
                val processed = newPosition.coerceIn(0.1f..1.0f)
                sliderPosition = processed
                onBrushWidthChange(processed * 100)
            }
        )
    }
}

@Composable
fun ButtonPanel(
    onClearClick: () -> Unit,
    onCapClick: (StrokeCap) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(
            modifier = Modifier
                .size(50.dp),
            onClick = onClearClick
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Clear", style = MaterialTheme.typography.titleMedium)
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier
                    .size(50.dp),
                onClick = { onCapClick(StrokeCap.Butt) }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Butt", style = MaterialTheme.typography.titleMedium)
                    Icon(
                        painter = painterResource(id = R.drawable.butt),
                        contentDescription = "Butt Cap"
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .size(50.dp),
                onClick = { onCapClick(StrokeCap.Round) }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Round", style = MaterialTheme.typography.titleMedium)
                    Icon(
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = "Round Cap"
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .size(50.dp),
                onClick = { onCapClick(StrokeCap.Square) }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Square", style = MaterialTheme.typography.titleMedium)
                    Icon(
                        painter = painterResource(id = R.drawable.square),
                        contentDescription = "Square Cap"
                    )
                }
            }
        }
    }
}


