package com.example.myapplication.interpretor.nodes

import androidx.compose.runtime.ReusableComposeNode

class InitArrayNode(val name: String, val size: Node): Node() {
    override fun work(dataWork: DataWork) {
        val sizeArr = when (size){
            is NumberNode -> size.calculate()
            is VariableNode -> size.calculate(dataWork)
            is Expression -> size.calculate(dataWork)
            else -> throw RuntimeException("Size is uncalculate")
        }

        if (sizeArr < 0) throw RuntimeException("Array size must be > 0")
        dataWork.arrays[name] = MutableList(sizeArr) { 0 }
    }

}