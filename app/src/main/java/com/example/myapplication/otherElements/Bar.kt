package com.example.myapplication.otherElements

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource

@Composable
fun Bar (navController: NavHostController) {
    val colors = MaterialTheme.colorScheme

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 48.dp)
        .background(Color.Gray)
        .drawWithContent {
            drawContent()
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 2.dp.toPx()
            )
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 2.dp.toPx()
            )
        },
        horizontalArrangement = Arrangement
            .SpaceEvenly
    )
    {
        BarButton(navController, "build",
            stringResource(R.string.build_Alg))

        BarButton(navController, "info",
            stringResource(R.string.infa))

        BarButton(navController, "scripts",
            stringResource(R.string.rdy_scripts))

    }
}