package com.example.myapplication.interpretor.nodes

class PrintString(val literal: String):Node() {
    override fun work(dataWork: DataWork) {
        dataWork.printOutput.add(literal)
    }
}