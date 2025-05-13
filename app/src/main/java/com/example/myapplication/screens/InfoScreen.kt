package com.example.myapplication.screens

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.otherElements.Bar
import com.example.myapplication.R

@Composable
fun InfoScreen (navController: NavHostController) {
    Bar(navController)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement
            .SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))

                .border(width = 3.dp, color = Color.Black)
                .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
                .background(Color.Gray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.info),
                textAlign = TextAlign.Justify,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(width = 3.dp, color = Color.Black)
                .width((LocalConfiguration.current.screenWidthDp * 0.95).dp)
                .background(Color.White)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement
                    .SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                SocialLinkText(
                    "GitHub проекта: Mobile Codeblock Interpreter",
                    "Mobile Codeblock Interpreter",
                    "https://github.com/brvinxdvnce/Mobile-Codeblock-interpreter"
                );

                Divider (
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(4.dp),
                    color = Color.Black
                )

                Text(
                    "Команда разработки:",
                    textAlign = TextAlign.Justify
                )

                SocialLinkText("brvinxdvnce", "brvinxdvnce", "https://github.com/brvinxdvnce");
                SocialLinkText("Egor Piven", "Egor Piven", "https://github.com/egorCoolBoy");
                SocialLinkText("TougGuy03", "TougGuy03", "https://github.com/TougGuy03");
            }
        }

    }

}

@Composable
fun SocialLinkText(text : String, linkText : String, linkUrl : String ) {

    val annotatedString = buildAnnotatedString {
        append(text)
        val startIndex = text.indexOf(linkText)
        val endIndex = startIndex + linkText.length

        addStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            start = startIndex,
            end = endIndex
        )
        addStringAnnotation(
            tag = "URL",
            annotation = linkUrl,
            start = startIndex,
            end = endIndex
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString,
        modifier = Modifier,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}
