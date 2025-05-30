package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myapplication.ScriptSaving.CustomScriptRepository
import com.example.myapplication.screens.ReadyScriptsScreen
import com.example.myapplication.screens.InfoScreen
import com.example.myapplication.screens.BuildScreen
import com.example.myapplication.screens.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CustomScriptRepository.loadScripts(applicationContext)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash") {
                composable("build") {
                    BuildScreen(navController)
                }
                composable("scripts") {
                    ReadyScriptsScreen(navController)
                }
                composable("info") {
                    InfoScreen(navController)
                }
                composable("splash") {
                    SplashScreen(navController)
                }
            }
        }
    }
}

@Composable
fun ReadyScriptsScreen (navController: NavHostController) { }

@Composable
fun InfoScreen (navController: NavHostController) { }

@Composable
fun BuildScreen (navController: NavHostController) { }