package com.example.myapplication.interpretor.nodes

class DeclareNode(val name: String, val nodeValue: Node?):Node() {
    override fun work(dataWork: DataWork) {
        val value = when(nodeValue){
            is NumberNode -> nodeValue.calculate()
            is VariableNode -> nodeValue.calculate(dataWork)
            is Expression -> nodeValue.calculate(dataWork)
            else -> 0
        }
        dataWork.variables[name] = value
    }
}