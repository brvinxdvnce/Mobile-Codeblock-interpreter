package com.example.myapplication.otherElements

import androidx.compose.material3.Button
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
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource


@Composable
fun Bar (navController: NavHostController) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)
        .background(Color.Gray),
        horizontalArrangement = Arrangement
            .SpaceEvenly
    )
    {
        Button(onClick = {navController.navigate("build") { launchSingleTop = true } })
        { Text(text = stringResource(R.string.build_Alg)) }

        Button(onClick = { navController.navigate("scripts") { launchSingleTop = true } })
        { Text(text = stringResource(R.string.rdy_scripts)) }

        Button(onClick = { navController.navigate("info") { launchSingleTop = true } })
        { Text(text = stringResource(R.string.infa)) }

    }
}