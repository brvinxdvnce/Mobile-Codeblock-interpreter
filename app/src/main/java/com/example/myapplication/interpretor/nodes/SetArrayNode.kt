package com.example.myapplication.interpretor.nodes

import java.lang.RuntimeException

class SetArrayNode(val name: String, val index: Node, val value: Node): Node() {
    override fun work(dataWork: DataWork) {
        val indexArr = when (index){
            is NumberNode -> index.calculate()
            is VariableNode -> index.calculate(dataWork)
            is Expression -> index.calculate(dataWork)
            else -> throw RuntimeException("Index is uncalculate")
        }
        val valueArr = when (value){
            is NumberNode -> value.calculate()
            is VariableNode -> value.calculate(dataWork)
            is Expression -> value.calculate(dataWork)
            else -> throw RuntimeException("Value is uncalculate")
        }
        val array = dataWork.arrays[name]
            ?: throw RuntimeException("Array $name not found")
        if (indexArr !in array.indices)
            throw RuntimeException("Index $index out of range")
        array[indexArr] = valueArr
    }
}