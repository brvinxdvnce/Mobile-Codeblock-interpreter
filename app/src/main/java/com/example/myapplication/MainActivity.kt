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
import androidx.compose.ui.res.stringResource
import com.example.myapplication.screens.ReadyScriptsScreen
import com.example.myapplication.screens.InfoScreen
import com.example.myapplication.otherElements.Bar
import com.example.myapplication.otherElements.AddElemBar
import com.example.myapplication.screens.BuildScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "info") {
                composable("build") {
                    BuildScreen(navController)
                }
                composable("scripts") {
                    ReadyScriptsScreen(navController)
                }
                composable("info") {
                    InfoScreen(navController)
                }
            }
        }
    }
}

@Composable
fun ReadyScriptsScreen (navController: NavHostController) { }

// хз решил не трогать

@Composable
fun SetScript (name: String) {
    Text(name)
    Button(onClick = {
        // переход в окно play ->
        // генерация скрипта
    }) { }
}

@Composable
fun InfoScreen (navController: NavHostController) { }


@Composable
fun BuildScreen (navController: NavHostController) { }

