package com.example.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.BuildConfig


@Composable
fun SplashScreen(navController: NavHostController) {
    val colors = MaterialTheme.colorScheme

    Box(
       modifier = Modifier
           .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Black),
            verticalArrangement = Arrangement
                .SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement
                    .spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size((LocalConfiguration.current.screenWidthDp * 0.6).dp),
                    painter = painterResource(id = R.drawable.hits_logo),
                    contentDescription = ""
                )

                Text(
                    "HITs. CodeBlocks.",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { navController.navigate("info") },
                colors = ButtonDefaults.buttonColors(White),
                modifier = Modifier.size(
                    width = (LocalConfiguration.current.screenWidthDp * 0.6).dp,
                    height = (LocalConfiguration.current.screenWidthDp * 0.2).dp)
                    .clip(RoundedCornerShape(15.dp)),
                shape = RectangleShape,
            ) {
                Text(
                    text = "Start program",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            "version: ${BuildConfig.VERSION_NAME}",
            color = Color.DarkGray,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}
