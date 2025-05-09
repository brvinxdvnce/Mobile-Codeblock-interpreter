package com.example.myapplication.interpretor.nodes

class VariableNode(val name: String) : Node(), Calculate {
    override fun work(dataWork: DataWork) {
    }

    override fun calculate(dataWork: DataWork): Int {
        return dataWork.variables[name] ?: throw RuntimeException("Переменная '$name' не инициализирована")
    }


}