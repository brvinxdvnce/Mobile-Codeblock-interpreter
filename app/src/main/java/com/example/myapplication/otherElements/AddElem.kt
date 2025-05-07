package com.example.myapplication.otherElements

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


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
                text = { Text("tmp") },
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("array") },
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("arithmetic") },
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("if") },
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("else if") },
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("compare") },
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("for") },
                onClick = {
                    //add elem from back
                    expanded = false
                }
            )
        }
    }
}