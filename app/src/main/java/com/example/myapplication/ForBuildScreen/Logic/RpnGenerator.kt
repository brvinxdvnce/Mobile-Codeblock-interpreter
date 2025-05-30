package com.example.myapplication.ForBuildScreen.Logic

import com.example.myapplication.ForBuildScreen.BlockType

fun generateRpn(type: BlockType, content: String, declaredVariables: List<String>): String {
    return when (type) {
        BlockType.VARIABLE_DECLARATION -> generateVariableDeclarationRpn(content)
        BlockType.ASSIGNMENT -> generateAssignmentRpn(content, declaredVariables)
        BlockType.ARRAY_DECLARATION-> generateArrayDeclaration(content,declaredVariables)
        BlockType.ARRAY_ASSIGNMENT -> assignmentArray(content,declaredVariables)
        BlockType.IF -> generateConditionRpn(content, declaredVariables, "@true")
        BlockType.WHILE -> generateConditionRpn(content, declaredVariables, "@while")
        BlockType.ELSE -> "@false"
        BlockType.ENDIF -> "@endif"
        BlockType.ENDWHILE -> "@endwhile"
        BlockType.PRINT -> generatePrintRpn(content, declaredVariables)
        else -> ""
    }
}

fun generateVariableDeclarationRpn(content: String): String {
    val variableName = content.trim()
    return when {
        !isValidVariableName(variableName) -> "ERROR"
        else -> "$variableName 0 =" // по дефолту нолик
    }
}

fun generateAssignmentRpn(content: String, declaredVariables: List<String>): String {
    val (left, right) = parseAssignment(content) ?: return "ERROR"
    val rpnRight : String
    return when {
        !isValidVariableName(left) -> "ERROR"
        !declaredVariables.contains(left) -> "ERROR"
        else -> {
            if (isArray(right))
            {
                 rpnRight = convertArrayToRpn(right,declaredVariables)
            }
            else
            {
                 rpnRight = convertArithmeticToRpn(right, declaredVariables)
            }
            if (rpnRight == "ERROR") "ERROR" else "$left $rpnRight ="
        }
    }
}

fun generateConditionRpn(condition: String, declaredVariables: List<String>, jumpLabel: String): String {
    val (left, operator, right) = parseCondition(condition) ?: return "ERROR"

    //если обе части массивы
    if (isArray(left) && isArray(right))
    {
        val leftRpn = convertArrayToRpn(left,declaredVariables)
        val rightRpn = convertArrayToRpn(right,declaredVariables)
        return when {
            leftRpn.contains("ERROR") || rightRpn.contains("ERROR") -> "ERROR"
            else -> "$leftRpn $rightRpn $operator $jumpLabel"
        }
    }
    // чекаем левую часть
    val leftRpn = when {
        isNumber(left) -> left
        isValidVariableName(left) && declaredVariables.contains(left) -> left
        else -> try {
            convertArithmeticToRpn(left, declaredVariables)
        } catch (e: Exception) {
            "ERROR"
        }
    }

    // чекаем правую часть
    val rightRpn = when {
        isNumber(right) -> right
        isValidVariableName(right) && declaredVariables.contains(right) -> right
        else -> try {
            convertArithmeticToRpn(right, declaredVariables)
        } catch (e: Exception) {
            "ERROR"
        }
    }

    return when {
        leftRpn.contains("ERROR") || rightRpn.contains("ERROR") -> "ERROR"
        else -> "$leftRpn $rightRpn $operator $jumpLabel"
    }
}

fun generatePrintRpn(content: String, declaredVariables: List<String>): String {
    return when {
        isArray(content) -> "${convertArrayToRpn(content,declaredVariables)} print"
        isStringLiteral(content) ->
            if (content.length >= 2) "$content print" else "ERROR"
        isNumber(content) -> "$content print"
        isValidVariableName(content) ->
            if (declaredVariables.contains(content)) "$content print"
            else "ERROR"
        else -> {
            val rpn = convertArithmeticToRpn(content, declaredVariables)
            if (rpn.contains("ERROR")) rpn else "$rpn print"
        }
    }
}

//преобразует и проверяет строку выражения в рпн
fun convertArithmeticToRpn(expression: String, declaredVariables: List<String>): String {
    return try {
        val tokens = tokenizeArithmeticExpression(expression)
        validateArithmeticTokens(tokens, declaredVariables)
        arithmeticTokensToRpn(tokens)
    } catch (e: Exception) {
        "ERROR"
    }
}