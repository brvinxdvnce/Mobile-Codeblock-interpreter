package com.example.myapplication.ForBuildScreen

data class CodeBlock(
    val id: Int,
    var type: BlockType = BlockType.PLACEHOLDER,
    var content: String = "",
    var rpn: String = ""
)

enum class BlockType {
    PLACEHOLDER, VARIABLE_DECLARATION,ASSIGNMENT, IF, ELSE, ENDIF, WHILE, ENDWHILE, PRINT
}