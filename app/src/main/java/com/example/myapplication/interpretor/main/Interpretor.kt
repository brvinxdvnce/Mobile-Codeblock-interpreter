package com.example.myapplication.interpretor.main

import com.example.myapplication.interpretor.nodes.*
import com.example.myapplication.interpretor.parser.Parser

class Interpretor {
    fun run(code: List<String>): List<String>{
        val parser = Parser()
        val nodes = parser.parser(code)
        val data = DataWork()
        nodes.forEach { it.work(data) }
        return data.printOutput
    }

}