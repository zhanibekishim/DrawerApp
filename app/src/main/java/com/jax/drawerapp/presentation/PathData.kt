package com.jax.drawerapp.presentation


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap

data class PathData(
    val path: Path = Path(),
    val color: Color = Color.Blue,
    val brushWidth: Float = 0f,
    val cap: StrokeCap = StrokeCap.Butt
)
