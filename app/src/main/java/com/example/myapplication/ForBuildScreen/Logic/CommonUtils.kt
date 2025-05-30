package com.example.myapplication.ForBuildScreen.Logic

fun isValidVariableName(name: String): Boolean {
    return name.matches(Regex("^[a-zA-Z_]\\w*$"))
}

fun isNumber(token: String): Boolean {
    return token.matches(Regex("""^\d+$""")) //
}

fun isStringLiteral(value: String): Boolean {
    return value.startsWith("'") && value.endsWith("'")
}

fun isArray(content: String) : Boolean
{
    return content.matches(Regex(""""^[a-zA-Z_]\\w*\\[(\\d+|[a-zA-Z_]\\w*)\\]$"""))
}

fun getOperatorPrecedence(op: String): Int = when (op) {
    "*", "/","%",  -> 3
    "+", "-" -> 2
    else -> 0
}