package com.example.myapplication.otherElements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp

@Composable
fun SetUserScriptButton (
    onDismiss: () -> Unit,
    onSave: (name : String, description : String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,

        confirmButton = {
            TextButton(onClick =  {
                onSave(name, description)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Save script")
        },
        text = {
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Script Name") }
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Script description") }
                )
            }
        }
    )
}