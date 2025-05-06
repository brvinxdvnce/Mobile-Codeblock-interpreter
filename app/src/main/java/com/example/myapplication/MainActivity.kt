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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity

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
        Button(onClick  = { navController.navigate("play" ) }) { Text("Try solve") }
        Button(onClick  = { navController.navigate("ready") }) { Text("Ready scripts") }
        Button(onClick  = { navController.navigate("info" ) }) { Text("Information") }
    }
}

@Composable
fun ReadyScripts (navController: NavHostController) {
    Column () {
        Bar(navController)

        Box() {
            Button(onClick = {
                navController.navigate("play" )
                // генерация скрипта
            }) { Text("Bubble sort") }
        }
        Box() {
            Button(onClick = {
                navController.navigate("play" )
                // генерация скрипта
            }) { Text("algos") }
        }
        Box() {
            Button(onClick = {
                navController.navigate("play" )
                // генерация скрипта
            }) { Text("algos") }
        }
        Box() {
            Button(onClick = {
                navController.navigate("play" )
                // генерация скрипта
            }) { Text("algos") }
        }
    }
}

@Composable
fun SetScript (name: String) {
    Text(name)
    Button(onClick = {
        // переход в окно play ->
        // генерация скрипта
    }) { }
}

@Composable
fun Info (navController: NavHostController) {
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
            Text("Это мобильное приложение помогает легко создавать и запускать алгоритмы прямо на вашем телефоне. Вы строите алгоритмы из простых блоков - например, условий и циклов - и сразу видите, как они работают.\n" +
                    "\n" +
                    "Приложение не требует сложных настроек и компиляции - оно сразу выполняет ваш алгоритм, показывая результат пошагово. Это удобно для обучения и быстрого тестирования идей.",
                textAlign = TextAlign.Center)
        }

        Box (
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("Это мобильное приложение помогает легко создавать и запускать алгоритмы прямо на вашем телефоне. Вы строите алгоритмы из простых блоков - например, условий и циклов - и сразу видите, как они работают.\n" +
                    "\n" +
                    "Приложение не требует сложных настроек и компиляции - оно сразу выполняет ваш алгоритм, показывая результат пошагово. Это удобно для обучения и быстрого тестирования идей.",
                textAlign = TextAlign.Center)
        }

    }
}


@Composable
fun Play (navController: NavHostController) {
    Column {
        Bar(navController)
        AddElemBar()
    }

}

@Composable
fun AddElemBar () {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Button(onClick = {expanded = !expanded}) {
            Text(text = "add elem")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            DropdownMenuItem(
                text = {Text("tmp")},
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("array")},
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("arithmetic")},
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("if")},
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("else if")},
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("compare")},
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("for")},
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
        }
    }
}