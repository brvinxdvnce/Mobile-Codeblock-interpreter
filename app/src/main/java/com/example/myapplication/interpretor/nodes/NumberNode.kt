package com.example.myapplication.interpretor.nodes

class NumberNode(val number: Int) : Node() {
    override fun work(dataWork: DataWork) {}

    fun calculate(): Int {
        return number
    }
}