package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.bubble_sort),
                    stringResource(R.string.bubble_sort_info),
                );
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.algos_1),
                    stringResource(R.string.algos_1_info)
                );
            }
            item {
                ScriptButton(navController, stringResource(R.string.algos_2));
            }
            item {
                ScriptButton(navController, stringResource(R.string.algos_3));
            }
            item {
                ScriptButton(navController, stringResource(R.string.algos_3));
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.bubble_sort),
                    stringResource(R.string.bubble_sort_info),
                );
            }
            item {
                ScriptButton(navController, stringResource(R.string.algos_3));
            }
            item {
                ScriptButton(
                    navController,
                    stringResource(R.string.bubble_sort),
                    stringResource(R.string.bubble_sort_info),
                );
            }
            item {
                ScriptButton(navController, stringResource(R.string.algos_3));
            }
        }
    }
}