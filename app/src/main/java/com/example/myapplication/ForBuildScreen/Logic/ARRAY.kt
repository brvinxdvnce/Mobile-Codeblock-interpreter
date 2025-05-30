package com.example.myapplication.ForBuildScreen.Logic

fun generateArrayDeclaration(content: String, declaredVariables: List<String>): String {
    val regex = Regex("""^([a-zA-Z_]\w*)\[(\d+)\]\s*$""")
    val matchResult = regex.find(content)

    if (matchResult == null) {
        return "ERROR"
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

fun convertArrayToRpn(content: String, declaredVariables: List<String>): String {
    val regex = Regex("^([a-zA-Z_]\\w*)\\[(.+)\\]$")
    val matchResult = regex.find(content) ?: return "ERROR"
    val (name, indexExpr) = matchResult.destructured
    val cleanedIndexExpr = indexExpr.trim()
    val rpnIdx = convertArithmeticToRpn(cleanedIndexExpr, declaredVariables)

    if (rpnIdx == "ERROR") return "ERROR"
    return "$name $rpnIdx []"
}

fun assignmentArray(content: String, declaredVariables: List<String>): String {
    val parts = content.split("=", limit = 2).map { it.trim() }
    if (parts.size != 2) {
        return "ERROR"
    }

    val (leftPart, rightPart) = parts

    if (isArray(leftPart) && isArray(rightPart)) {
        //лево
        val leftRegex = Regex("^([a-zA-Z_]\\w*)\\[(.+)\\]$")
        val leftMatch = leftRegex.find(leftPart) ?: return "ERROR"
        val (leftName, leftIndex) = leftMatch.destructured
        //право
        val rightRegex = Regex("^([a-zA-Z_]\\w*)\\[(.+)\\]$")
        val rightMatch = rightRegex.find(rightPart) ?: return "ERROR"
        val (rightName, rightIndex) = rightMatch.destructured

        val rpnLeftIndex = convertArithmeticToRpn(leftIndex.trim(), declaredVariables)
        if (rpnLeftIndex == "ERROR") return "ERROR"

        val rpnRightIndex = convertArithmeticToRpn(rightIndex.trim(), declaredVariables)
        if (rpnRightIndex == "ERROR") return "ERROR"

        return "$rightName $rpnRightIndex [] $leftName $rpnLeftIndex []="
    }

    //адекватное присвоение
    val arrayRegex = Regex("^([a-zA-Z_]\\w*)\\[(.+)\\]$")
    val arrayMatch = arrayRegex.find(leftPart) ?: return "ERROR"

    val (name, indexExpr) = arrayMatch.destructured
    val cleanedIndexExpr = indexExpr.trim()


    val rpnIdx = convertArithmeticToRpn(cleanedIndexExpr, declaredVariables)
    if (rpnIdx == "ERROR") return "ERROR"


    val rpnValue = convertArithmeticToRpn(rightPart, declaredVariables)
    if (rpnValue == "ERROR") return "ERROR"

    return "$name $rpnIdx $rpnValue []="
}



