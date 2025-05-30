package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

@Composable
fun OutputConsoleScreen (
    onClose: () -> Unit,
    content : List<String> = List(100) {listOf("привет")}.flatten(),
    showSaveDialog: Boolean,
    onShowSaveDialogChange: (Boolean) -> Unit
    ) {
    val colors = MaterialTheme.colorScheme

    Box (modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height((LocalConfiguration.current.screenHeightDp * 0.5).dp)
                .align(Alignment.BottomCenter),
            color = Color.DarkGray
        ) {
            Column()
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        onShowSaveDialogChange(true)
                    },
                        modifier = Modifier) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp),
                            tint = Color.White)
                    }

                    Divider(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(0.75f),
                        thickness = 4.dp,
                        color = Color.White)

                    IconButton(onClick = onClose,
                        modifier = Modifier) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier.size(28.dp),
                            tint = Color.White
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(content) { str ->
                        Text(
                            str,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}


