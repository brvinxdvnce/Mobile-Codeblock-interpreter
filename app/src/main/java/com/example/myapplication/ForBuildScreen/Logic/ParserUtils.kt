package com.example.myapplication.ForBuildScreen.Logic

fun parseAssignment(content: String): Pair<String, String>? {
    val parts = content.split("=", limit = 2).map { it.trim() }
    if (parts.size != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
        return null
    }
    return parts[0] to parts[1]
}
fun validateArithmeticTokens(tokens: List<String>, declaredVariables: List<String>) {
    if (tokens.isEmpty()) {
        throw IllegalArgumentException("ERROR")
    }

    var expectOperand = true
    var parenCount = 0
    for (i in tokens.indices) {
        val token = tokens[i]
        when {
            token == "(" -> {
                if (!expectOperand) {
                    throw IllegalArgumentException("ERROR")
                }
                parenCount++
            }
            token == ")" -> {
                if (expectOperand || parenCount <= 0) {
                    throw IllegalArgumentException("ERROR)")
                }
                parenCount--
                expectOperand = false
            }
            isNumber(token) -> {
                if (!expectOperand) {
                    throw IllegalArgumentException("ERROR")
                }
                expectOperand = false
            }
            isValidVariableName(token) -> {
                if (!expectOperand) {
                    throw IllegalArgumentException("ERROR")
                }
                if (!declaredVariables.contains(token)) {
                    throw IllegalArgumentException("ERROR")
                }
                expectOperand = false
            }
            token in listOf("+", "-", "*", "/", "%") -> {
                if (expectOperand && token != "-") {
                    throw IllegalArgumentException("ERROR")
                }
                if (token == "-" && expectOperand && (i == 0 || tokens[i-1] == "(")) {
                    continue
                }
                expectOperand = true
            }
            else -> throw IllegalArgumentException("ERROR")
        }
    }

    if (expectOperand || parenCount != 0) {
        throw IllegalArgumentException("ERROR")
    }
}

fun arithmeticTokensToRpn(tokens: List<String>): String {
    val output = mutableListOf<String>()
    val operators = mutableListOf<String>()

    for (i in tokens.indices) {
        val token = tokens[i]
        when {
            isNumber(token) || isValidVariableName(token) -> output.add(token)
            token == "(" -> operators.add(token)
            token == ")" -> {
                while (operators.isNotEmpty() && operators.last() != "(") {
                    output.add(operators.removeAt(operators.size - 1))
                }
                if (operators.isNotEmpty()) operators.removeAt(operators.size - 1) // Удаляем "("
            }
            else -> {
                if (token == "-" && (i == 0 || tokens[i-1] in listOf("(", "+", "-", "*", "/", "%"))) {
                    operators.add("-")
                } else {
                    while (operators.isNotEmpty() && operators.last() != "(" &&
                        getOperatorPrecedence(operators.last()) >= getOperatorPrecedence(token)
                    ) {
                        output.add(operators.removeAt(operators.size - 1))
                    }
                    operators.add(token)
                }
            }
        }
    }

    while (operators.isNotEmpty()) {
        output.add(operators.removeAt(operators.size - 1))
    }

    return output.joinToString(" ")
}

fun tokenizeArithmeticExpression(expression: String): List<String> {
    val regex = Regex("""([a-zA-Z_]\w*|\d+|[+\-*/%()])""")
    return regex.findAll(expression).map { it.value }.toList()
}

fun parseCondition(condition: String): Triple<String, String, String>? {
    // ищем сравнение
    val operatorMatch = Regex("""([<>]=?|==|!=)""").find(condition) ?: return null
    val operator = operatorMatch.value

    // разделяем на левую и правую часть
    val leftPart = condition.substring(0, operatorMatch.range.first).trim()
    val rightPart = condition.substring(operatorMatch.range.last + 1).trim()

    return Triple(leftPart, operator, rightPart)
}