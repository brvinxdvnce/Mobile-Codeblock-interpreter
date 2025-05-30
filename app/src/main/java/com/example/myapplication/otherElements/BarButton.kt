package com.example.myapplication.otherElements

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BarButton(navController : NavHostController, route: String, string : String) {

    Button(
        onClick = {
            navController.navigate(route) {
                launchSingleTop = true
            }
        },
        colors = ButtonDefaults.buttonColors(Black),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp)),
        shape = RectangleShape
    ) {
        Text(text = string)
    }
}