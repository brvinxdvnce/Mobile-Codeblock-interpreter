package com.example.myapplication.ScriptSaving

import androidx.compose.ui.res.stringResource
import com.example.myapplication.ForBuildScreen.CodeBlock
import com.example.myapplication.R

object SystemScriptRepository {

    val scripts = mutableListOf<Script>(
        Script(
            name = "Bubble sort",
            description = "Когда алгоритм так медленно думает, что даже улитка начинает нервничать и просить ускориться.",
            blocks = mutableListOf<CodeBlock>()
        ),
        Script(
            name = "Sum of 2",
            description = "2 + 2 сложить ты сможешь.",
            blocks = mutableListOf<CodeBlock>()
        ),
        Script(
            name = "Hello, world!",
            description = "по базе",
            blocks = mutableListOf<CodeBlock>()
        ),
        Script(
            name = "Factorial func",
            description = "n*(n-1)*...*1, ничего особенного ",
            blocks = mutableListOf<CodeBlock>()
        ),
        Script(
            name = "Fibonacci sequence",
            description = "Не увлекайтесь рекурсией!",
            blocks = mutableListOf<CodeBlock>()
        ),
    )
}
