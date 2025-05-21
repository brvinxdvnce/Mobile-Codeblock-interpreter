package com.example.myapplication.interpretor.nodes

class WhileNode(val condition: CompareNode, val body: List<Node>):Node() {
    override fun work(dataWork: DataWork) {
        while(condition.calculate(dataWork) == 1){
            body.forEach { it.work(dataWork) }
        }
    }


}