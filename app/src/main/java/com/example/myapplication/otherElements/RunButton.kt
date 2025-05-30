package com.example.myapplication.otherElements

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun RunButton(onClick: ()-> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.05f)
            .clip(RoundedCornerShape(10.dp)),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black)
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = ""
        )
    }
}
