package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.otherElements.Bar
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ScriptSaving.CustomScriptRepository
import com.example.myapplication.ScriptSaving.SystemScriptRepository
import com.example.myapplication.otherElements.ScriptButton

@Composable
fun ReadyScriptsScreen(navController: NavHostController) {
    val colors = MaterialTheme.colorScheme

    Box {
        Column (modifier = Modifier
            .background(colors.background)
        ) {
            Bar(navController)

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Text(
                        stringResource(R.string.ready_scripts_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp)
                }

                items(SystemScriptRepository.scripts) { script ->
                    ScriptButton(navController, script.name, script.description)
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp),
                        color = Color.Black,
                        thickness = 4.dp
                    )
                }

                item {
                    Text(
                        stringResource(R.string.custom_scripts_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                items(CustomScriptRepository.scripts) { script ->
                    ScriptButton(navController, script.name, script.description)
                }
            }
        }
    }
}