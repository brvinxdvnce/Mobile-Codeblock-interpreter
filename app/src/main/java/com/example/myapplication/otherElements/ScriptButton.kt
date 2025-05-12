package com.example.myapplication.otherElements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ScriptButton (navController: NavHostController, text : String, infotext : String = "") {
    Box {
        Button(onClick = {
            navController.navigate("build")
            // генерация скрипта
        }, modifier = Modifier
            .width((LocalConfiguration.current.screenWidthDp * 0.48).dp)
            .clip(RoundedCornerShape(10.dp)),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black)
        ) {
            Column () {
                Text(text = text, fontSize = 16.sp)

                Divider (
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(4.dp),
                    color = Color.White
                )

                Text(text = infotext, fontSize = 16.sp, textAlign = TextAlign.Justify)
            }
        }
    }
}