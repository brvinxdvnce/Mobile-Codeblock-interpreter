package com.example.myapplication.interpretor.nodes

import java.lang.RuntimeException

class SetArrayNode(val name: String, val index: Node, val value: Node): Node() {
    override fun work(dataWork: DataWork) {
        val indexArr = (index as? Calculate)?.calculate(dataWork)
            ?:throw RuntimeException("Index is not calculate")
        val valueArr = (value as? Calculate)?.calculate(dataWork)
            ?:throw RuntimeException("Value is not calculate")
        val array = dataWork.arrays[name]
            ?: throw RuntimeException("Array $name not found")
        if (indexArr !in array.indices)
            throw RuntimeException("Index $index out of range")
        array[indexArr] = valueArr
    }
}