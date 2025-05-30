package com.example.myapplication.ScriptSaving

import android.content.Context
import com.example.myapplication.ForBuildScreen.CodeBlock

object CustomScriptRepository {
    var scripts = mutableListOf<Script>(
        Script(
            name = "Описание",
            description = "Попробуйте добавить свой алгоритм во вкладе Build Code! <в консоли>",
            blocks = mutableListOf<CodeBlock>()
        )
    )

    fun loadScripts(context : Context) {
        scripts = ScriptStorage.loadScripts(context).toMutableList()
    }
}