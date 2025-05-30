package com.example.myapplication.interpretor.nodes

class BlockNode(val children: List<Node>) : Node() {
    override fun work(dataWork: DataWork) {
        for (node in children) {
            node.work(dataWork)
        }
    }
}