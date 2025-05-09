package com.example.myapplication.interpretor.nodes

class CompareNode(val leftNode: Node, val compareString: String, val rightNode: Node): Node(), Calculate {
    override fun work(dataWork: DataWork) {

    }

    override fun calculate(dataWork: DataWork): Int {
        val l = when(leftNode){
            is NumberNode -> leftNode.calculate()
            is VariableNode -> leftNode.calculate(dataWork)
            is Expression -> leftNode.calculate(dataWork)
            else -> throw RuntimeException("Node is uncalculate")
        }

        val r = when(rightNode){
            is NumberNode -> rightNode.calculate()
            is VariableNode -> rightNode.calculate(dataWork)
            is Expression -> rightNode.calculate(dataWork)
            else -> throw RuntimeException("Node is uncalculate")
        }

        return when(compareString) {
            "<" -> if(l<r) 1 else 0
            ">" -> if(l>r) 1 else 0
            "<=" -> if(l<=r) 1 else 0
            ">=" -> if(l>=r) 1 else 0
            "==" -> if(l==r) 1 else 0
            "!=" -> if(l!=r) 1 else 0
            else -> throw RuntimeException("Operation is not correct")
        }
    }


}