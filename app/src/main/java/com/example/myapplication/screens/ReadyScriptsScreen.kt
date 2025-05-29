package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.otherElements.Bar
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.otherElements.ScriptButton

@Composable
fun ReadyScriptsScreen(navController: NavHostController) {

    Column {
        Bar(navController)


        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.bubble_sort),
                    stringResource(R.string.bubble_sort_info)
                )
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.sum_of_2),
                    stringResource(R.string.sum_of_2_info)
                )
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.hello_world),
                    stringResource(R.string.hello_world_info)
                )
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.factorial),
                    stringResource(R.string.factorial_info)
                )
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.fibbo_number),
                    stringResource(R.string.fibbo_number_info)
                )
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.bubble_sort),
                    stringResource(R.string.bubble_sort_info)
                )
            }
            item { ScriptButton(navController, stringResource(R.string.algos_3)) }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.bubble_sort),
                    stringResource(R.string.bubble_sort_info)
                )
            }
            item { ScriptButton(navController, stringResource(R.string.algos_3)) }

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

            item { ScriptButton(navController, stringResource(R.string.algos_3)) }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.bubble_sort),
                    stringResource(R.string.bubble_sort_info)
                )
            }
            item { ScriptButton(navController, stringResource(R.string.algos_3)) }
        }
    }
}