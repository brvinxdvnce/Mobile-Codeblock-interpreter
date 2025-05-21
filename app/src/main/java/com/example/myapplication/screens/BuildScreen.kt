package com.example.myapplication.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.myapplication.otherElements.AddElemBar
import com.example.myapplication.otherElements.Bar

@Composable
fun BuildScreens (navController: NavHostController) {

    Column () {
        Bar(navController)
        AddElemBar()
    }
}

@Composable
fun BuildScreen (navController: NavHostController) {
    var consoleVisibility = remember { mutableStateOf(false)}

    Column () {
        Bar(navController)
        AddElemBar()

        RunButton(
            onClick = {consoleVisibility.value = true}
        )
    }
    if (consoleVisibility.value) {
        OutputConsoleScreen(onClose = {consoleVisibility.value = false})
    }
}
