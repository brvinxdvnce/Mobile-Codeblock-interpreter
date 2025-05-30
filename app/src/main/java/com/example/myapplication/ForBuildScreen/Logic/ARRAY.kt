package com.example.myapplication.ForBuildScreen.Logic

fun generateArrayDeclaration(content: String, declaredVariables: List<String>): String {
    val regex = Regex("""^([a-zA-Z_]\w*)\[(\d+)\]\s*$""")
    val matchResult = regex.find(content)
    if (matchResult == null) {
        return "ERROR" // Неправильный формат
    }
    val (name, size) = matchResult.destructured

    if (name in declaredVariables) {
        return "ERROR"
    }

    return when {
        isNumber(size) -> "$name $size init[]"
        else -> "ERROR"
    }
}