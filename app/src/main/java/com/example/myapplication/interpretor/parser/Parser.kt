package com.example.myapplication.interpretor.parser
import com.example.myapplication.interpretor.nodes.*


class Parser {
    fun parser(RPN : List<String>):List<Node>{
        val stackInput = mutableListOf<Node>()
        val stackOutput = mutableListOf<Node>()

        var i = 0
        while(i < RPN.size){
            val symbol = RPN[i]
            when{
                symbol.matches(Regex("-?[0-9]+")) ->
                    stackInput.add(NumberNode(symbol.toInt()))

                symbol.matches(Regex("^\".*\"$")) -> {
                    val literal = symbol.substring(1, symbol.length - 1)
                    stackInput.add(PrintString(literal))
                }

                symbol == "print" ->{
                    val value = stackInput.removeAt(stackInput.lastIndex)
                    when (value){
                        is VariableNode, is Expression, is CompareNode, is NumberNode ->
                            stackOutput.add(PrintNode(value))
                        is PrintString ->
                            stackOutput.add(value)

                    }
                }

                symbol in setOf("+", "-", "/", "*") -> {
                    val r = stackInput.removeAt(stackInput.lastIndex)
                    val l = stackInput.removeAt(stackInput.lastIndex)
                    stackInput.add(Expression(l, symbol, r))
                }

                symbol in setOf("<", ">", "<=", ">=", "==", "!=")->{
                    val r = stackInput.removeAt(stackInput.lastIndex)
                    val l = stackInput.removeAt(stackInput.lastIndex)
                    stackInput.add(CompareNode(l, symbol, r))
                }

                symbol =="="->{
                    val value = stackInput.removeAt(stackInput.lastIndex)
                    val variable = stackInput.removeAt(stackInput.lastIndex) as? VariableNode
                        ?: throw RuntimeException("Left of '=' must be variable")
                    stackOutput.add(AssignmentNode(variable.name, value))
                }

                symbol == "@true" ->{
                    i++
                    val trueList = mutableListOf<String>()
                    while (i < RPN.size && RPN[i] !in setOf("@false", "endif")){
                        trueList += RPN[i++]
                    }
                    val trueBlock = parser(trueList)
                    stackInput.add(object : Node(){
                        override fun work(dataWork: DataWork){trueBlock.forEach { it.work(dataWork) }}
                    })

                    continue
                }

                symbol == "@false" -> {
                    i++
                    val elseTokens = mutableListOf<String>()
                    while (i < RPN.size && RPN[i] != "@endif") {
                        elseTokens += RPN[i++]
                    }
                    val elseBlock = parser(elseTokens)
                    stackInput.add(object : Node() {
                        override fun work(dataWork: DataWork) = elseBlock.forEach { it.work(dataWork) }
                    })
                    continue
                }

                symbol =="@endif" -> {
                    val elseBlock = if(stackInput[stackInput.size] !is CompareNode && stackInput[stackInput.size-1] !is CompareNode){
                        stackInput.removeAt(stackInput.lastIndex)
                    } else{null}

                    val trueBlock = stackInput.removeAt(stackInput.lastIndex)

                    val condition = stackInput.removeAt(stackInput.lastIndex) as? CompareNode
                        ?: throw RuntimeException("If condition must be CompareNode")
                    stackOutput.add(ifNode(condition, listOf(trueBlock), elseBlock?.let{ listOf(it) }))

                }



                else -> if (symbol.matches(Regex("[a-zA-Z_]\\w*"))) {
                    stackInput.add(VariableNode(symbol))
                } else {
                    throw RuntimeException("Unknown symbol: $symbol")
                }
            }
            i++

        }

        return stackOutput
    }
}