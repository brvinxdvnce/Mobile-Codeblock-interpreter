package com.example.myapplication.otherElements

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RunButton(onClick: ()-> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black)
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = ""
        )
    }
}
