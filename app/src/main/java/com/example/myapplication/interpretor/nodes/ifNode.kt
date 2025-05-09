package com.example.myapplication.interpretor.nodes

class ifNode(val condition: Node, val trueCondition: List<Node>, val falseCondition: List<Node>?): Node() {
    override fun work(dataWork: DataWork) {
        val condValue  = when(condition){
            is CompareNode -> condition.calculate(dataWork)
            else -> throw RuntimeException("Its not compare")
        }
        if(condValue !=0){
            trueCondition.forEach{it.work(dataWork)}
        }
        else{
            falseCondition?.forEach{it.work(dataWork)}
        }

    }


}