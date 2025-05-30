package com.example.myapplication.interpretor.nodes

class GetArrayNode(val name: String, val index: Node): Node(), Calculate {
    override fun work(dataWork: DataWork) {
    }

    override fun calculate(dataWork: DataWork): Int {
        val indexArr = when (index){
            is NumberNode -> index.calculate()
            is VariableNode -> index.calculate(dataWork)
            is Expression -> index.calculate(dataWork)
            else -> throw RuntimeException("Index is uncalculate")
        }
        val array = dataWork.arrays[name]
            ?: throw RuntimeException("Array $name not found")
        if(indexArr !in array.indices)
            throw RuntimeException("index $indexArr is not in array range")
        return array[indexArr]
    }
}