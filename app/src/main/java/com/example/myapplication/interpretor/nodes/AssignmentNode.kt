package com.example.myapplication.interpretor.nodes

class AssignmentNode(val name: String, val nodeValue: Node):Node() {
    override fun work(dataWork: DataWork) {
        val value = when(nodeValue){
            is NumberNode -> nodeValue.calculate()
            is VariableNode -> nodeValue.calculate(dataWork)
            is Expression -> nodeValue.calculate(dataWork)
            else -> throw RuntimeException("Node is uncalculate")
        }
        dataWork.variables[name] = value
    }


}