package com.example.myapplication.ScriptSaving

import com.example.myapplication.ForBuildScreen.CodeBlock

data class Script(
    val name : String,
    val description : String,
    val blocks: List<CodeBlock>
)
