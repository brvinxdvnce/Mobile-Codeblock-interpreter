package com.example.myapplication.interpretor.nodes

class PrintNode(val varNode : Node?, val literal : String?) : Node(){
    override fun work(dataWork: DataWork) {
        val text = when{
            literal != null -> literal
            varNode != null -> {
                val value = (varNode as? Calculate)?.calculate(dataWork)?:throw RuntimeException("PrintNode: узел нельзя вычислить")
                value.toString()
            }
            else -> throw RuntimeException("PrintNode: нет ни literal ни exprNode")
        }
        dataWork.printOutput.add(text)
    }


}