package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.otherElements.Bar
import com.example.myapplication.R

@Composable
fun InfoScreen (navController: NavHostController) {
    Bar(navController)

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement
            .SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.info),
                textAlign = TextAlign.Center)
        }

        Box (
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.info2),
                textAlign = TextAlign.Center)
        }

    }
}
