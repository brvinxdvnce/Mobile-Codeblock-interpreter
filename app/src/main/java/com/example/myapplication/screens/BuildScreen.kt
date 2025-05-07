package com.example.myapplication.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.otherElements.AddElemBar
import com.example.myapplication.otherElements.Bar

@Composable
fun BuildScreen (navController: NavHostController) {
    Column {
        Bar(navController)
        AddElemBar()
    }

}
