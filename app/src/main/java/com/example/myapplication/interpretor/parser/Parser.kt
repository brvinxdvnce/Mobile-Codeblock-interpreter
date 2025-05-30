package com.example.myapplication.interpretor.parser
import androidx.compose.ui.res.booleanResource
import com.example.myapplication.interpretor.nodes.*


class Parser {
    fun parser(RPN : List<String>):List<Node>{
        val stackInput = mutableListOf<Node>()
        val stackOutput = mutableListOf<Node>()

        var i = 0
        while(i < RPN.size){
            val symbol = RPN[i]
            when{
                symbol.matches(Regex("-?[0-9]+")) ->{
                    stackInput.add(NumberNode(symbol.toInt()))
                    i++
                }

                symbol.matches(Regex("^\'.*\'$")) -> {
                    val literal = symbol.substring(1, symbol.length - 1)
                    stackInput.add(PrintString(literal))
                    i++
                }

                symbol == "print" ->{
                    val value = stackInput.removeAt(stackInput.lastIndex)
                    when (value){
                        is VariableNode, is Expression, is CompareNode, is NumberNode ->
                            stackOutput.add(PrintNode(value))
                        is PrintString ->
                            stackOutput.add(value)

                    }
                    i++
                }

                symbol in setOf("+", "-", "/", "*","%") -> {
                    val r = stackInput.removeAt(stackInput.lastIndex)
                    val l = stackInput.removeAt(stackInput.lastIndex)
                    stackInput.add(Expression(l, symbol, r))
                    i++
                }

                symbol in setOf("<", ">", "<=", ">=", "==", "!=")->{
                    val r = stackInput.removeAt(stackInput.lastIndex)
                    val l = stackInput.removeAt(stackInput.lastIndex)
                    stackInput.add(CompareNode(l, symbol, r))
                    i++
                }

                symbol =="="->{
                    val value = stackInput.removeAt(stackInput.lastIndex)
                    val variable = stackInput.removeAt(stackInput.lastIndex) as? VariableNode
                        ?: throw RuntimeException("Left of '=' must be variable")
                    stackOutput.add(AssignmentNode(variable.name, value))
                    i++
                }

                symbol == "@true" ->{
                    i++
                    val trueList = mutableListOf<String>()
                    var depth = 1
                    var flagExit: Boolean = false


                    while (i < RPN.size && depth > 0) {
                        val t = RPN[i]
                        when (t) {
                            "@true" -> {
                                depth++
                                trueList += t
                                i++
                            }
                            "@false" -> {
                                if (depth > 1) {
                                    trueList += t
                                    i++
                                } else {
                                    flagExit = true
                                    break
                                }
                            }
                            "@endif" ->{
                                if (depth > 1) {
                                    depth--
                                    trueList += t
                                    i++
                                } else {
                                    flagExit = true
                                    break
                                }
                            }
                            else -> {

                                trueList += t
                                i++
                            }
                        }
                    }

                    val trueBlock = parser(trueList)
                    stackInput.add(BlockNode(trueBlock))
                    if (flagExit == false){
                        continue
                    }
                }

                symbol == "@false" -> {
                    i++
                    val elseList = mutableListOf<String>()
                    var depth = 1
                    var flagExit: Boolean = false

                    while (i < RPN.size && depth > 0) {
                        val t = RPN[i]
                        when (t) {

                            "@true" -> {
                                depth++
                                elseList += t
                                i++
                            }
                            "@false" -> {
                                elseList += t
                                i++
                            }


                            "@endif" -> {
                                if (depth > 1) {
                                    depth--
                                    elseList += t
                                    i++
                                } else {
                                    flagExit = true
                                    break
                                }
                            }
                            else -> {
                                elseList += t
                                i++
                            }
                        }
                    }


                    val elseBlock = parser(elseList)

                    stackInput.add(BlockNode(elseBlock))

                    if (flagExit == false){
                        continue
                    }
                }

                symbol =="@endif" -> {
                    var elseBlock: List<Node>? = null

                    if (stackInput.size >= 3 &&
                        stackInput[stackInput.lastIndex] is BlockNode &&
                        stackInput[stackInput.lastIndex - 1] is BlockNode &&
                        stackInput[stackInput.lastIndex - 2] is CompareNode) {

                        elseBlock = (stackInput.removeAt(stackInput.lastIndex) as BlockNode).children
                    }

                    val trueBlock = (stackInput.removeAt(stackInput.lastIndex) as? BlockNode)?.children
                        ?: throw RuntimeException("Missing true block")

                    val condition = stackInput.removeAt(stackInput.lastIndex) as? CompareNode
                        ?: throw RuntimeException("If condition must be CompareNode")

                    stackOutput.add(ifNode(condition, trueBlock, elseBlock))
                    i++

                }

                symbol == "@while" -> {
                    i++
                    var flagExit: Boolean = false
                    val bodyTokens = mutableListOf<String>()
                    var depth = 1

                    while (i < RPN.size) {
                        val t = RPN[i]
                        when (t) {
                            "@while" -> {
                                depth++
                                bodyTokens += t
                                i++
                            }
                            "@endwhile" -> {
                                if (depth > 1) {
                                    depth--
                                    bodyTokens += t
                                    i++
                                } else {
                                    flagExit = true
                                    break
                                }
                            }
                            else -> {
                                bodyTokens += t
                                i++
                            }
                        }
                    }


                    val bodyNodes = parser(bodyTokens)
                    stackInput.add(BlockNode(bodyNodes))
                    if (flagExit == false){
                        continue
                    }
                }

                symbol == "@endwhile" ->{
                    val whileBlock = (stackInput.removeAt(stackInput.lastIndex) as? BlockNode)?.children
                        ?: throw RuntimeException("Missing while block")
                    val condition = stackInput.removeAt(stackInput.lastIndex) as? CompareNode
                        ?: throw RuntimeException("While condition must be CompareNode")
                    stackOutput.add(WhileNode(condition, whileBlock))
                    i++

                }

                symbol == "init[]" ->{
                    val size = stackInput.removeAt(stackInput.lastIndex)
                    val arrName = stackInput.removeAt(stackInput.lastIndex) as? VariableNode
                        ?: throw java.lang.RuntimeException("Array must be init as variable")
                    stackOutput.add(InitArrayNode(arrName.name, size))
                    i++

                }

                symbol == "[]=" ->{
                    val value = stackInput.removeAt(stackInput.lastIndex)
                    val index = stackInput.removeAt(stackInput.lastIndex)
                    val arr =  stackInput.removeAt(stackInput.lastIndex) as? VariableNode
                        ?: throw RuntimeException("Name must be variable")
                    stackOutput.add(SetArrayNode(arr.name, index, value))
                    i++
                }

                symbol =="[]" ->{
                    val index = stackInput.removeAt(stackInput.lastIndex)
                    val arr =  stackInput.removeAt(stackInput.lastIndex) as? VariableNode
                        ?: throw RuntimeException("Name must be variable")
                    stackInput.add(GetArrayNode(arr.name, index))
                    i++
                }




                else -> if (symbol.matches(Regex("[a-zA-Z_]\\w*"))) {
                    stackInput.add(VariableNode(symbol))
                    i++
                } else {
                    throw RuntimeException("Unknown symbol: $symbol")
                    i++
                }

            }


        }

        return stackOutput
    }
}