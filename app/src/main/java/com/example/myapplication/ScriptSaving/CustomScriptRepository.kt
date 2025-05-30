package com.example.myapplication.ScriptSaving

import com.example.myapplication.ForBuildScreen.CodeBlock

object CustomScriptRepository {
    val scripts = mutableListOf<Script>(
        Script(
            name = "Описание",
            description = "Попробуйте добавить свой алгоритм во вкладе Build Code! <в консоли>",
            blocks = mutableListOf<CodeBlock>()
        )
    )
}