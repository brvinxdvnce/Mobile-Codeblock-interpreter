package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import androidx.compose.foundation.layout.size


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost (navController = navController, startDestination = "info") {
                composable("play") {
                    Play(navController)
                }
                composable("ready") {
                    ReadyScripts(navController)
                }
                composable("info") {
                    Info(navController)
                }
            }
        }
    }
}

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
        Button(onClick  = { navController.navigate("play" ) }) { Text("play") }
        Button(onClick  = { navController.navigate("ready") }) { Text("scripts") }
        Button(onClick  = { navController.navigate("info" ) }) { Text("info") }
    }
}

@Composable
fun ReadyScripts (navController: NavHostController) {
    Bar(navController)
}

@Composable
fun Info (navController: NavHostController) {
    Box (modifier = Modifier.fillMaxSize()) {
        Bar(navController)

        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box (
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("hi", textAlign = TextAlign.Center)
            }
        }

//        Column (
//            modifier = Modifier.fillMaxSize(),
//
//        ) {
//            Text("dv",
//                modifier = Modifier.fillMaxSize(),
//                textAlign = TextAlign.Center)
//        }
    }
}

@Composable
fun Play (navController: NavHostController) {
    Bar(navController)


}

