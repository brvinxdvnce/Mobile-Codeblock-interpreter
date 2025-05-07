package com.example.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myapplication.otherElements.Bar
import androidx.navigation.NavHostController
import com.example.myapplication.R

@Composable
fun ReadyScriptsScreen(navController: NavHostController) {
    Column {
        Bar(navController)

        Box {
            Button(onClick = {
                navController.navigate("play")
                // генерация скрипта
            }) {
                Text(text = stringResource(R.string.bubble_sort))
            }
        }
        Box {
            Button(onClick = {
                navController.navigate("play")
                // генерация скрипта
            }) {
                Text(text = stringResource(R.string.algos_1))
            }
        }
        Box {
            Button(onClick = {
                navController.navigate("play")
                // генерация скрипта
            }) {
                Text(text = stringResource(R.string.algos_2))
            }
        }
        Box {
            Button(onClick = {
                navController.navigate("play")
                // генерация скрипта
            }) {
                Text(text = stringResource(R.string.algos_3))
            }
        }
    }
}
