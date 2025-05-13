package com.example.myapplication.interpretor.nodes

class PrintNode(val varNode : Node) : Node() {
    override fun work(dataWork: DataWork) {
        val text = (varNode as? Calculate)
            ?.calculate(dataWork)
            ?.toString()
            ?: throw RuntimeException("PrintNode: not calculate")

        dataWork.printOutput.add(text)
    }
}

