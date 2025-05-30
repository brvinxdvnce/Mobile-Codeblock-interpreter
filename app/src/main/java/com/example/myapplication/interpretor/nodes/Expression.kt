package com.example.myapplication.interpretor.nodes

class Expression(val leftItem: Node, val operation: String, val rightItem: Node) : Node(), Calculate {

    override fun work(dataWork: DataWork) {

    }

    override fun calculate(dataWork: DataWork) : Int{
        val l = when(leftItem){
            is NumberNode -> leftItem.calculate()
            is VariableNode -> leftItem.calculate(dataWork)
            is Expression -> leftItem.calculate(dataWork)
            else -> throw RuntimeException("Item is not correct")
        }

        val r = when(rightItem){
            is NumberNode -> rightItem.calculate()
            is VariableNode -> rightItem.calculate(dataWork)
            is Expression -> rightItem.calculate(dataWork)
            else -> throw RuntimeException("Item is not correct")
        }

        return when(operation) {
            "+" -> l + r
            "-" -> l - r
            "*" -> l * r
            "%" -> l % r
            "/" -> if (r != 0) l / r else throw ArithmeticException("Arithmetic Error")
            else -> throw RuntimeException("Operation is not correct")
        }


    }


}