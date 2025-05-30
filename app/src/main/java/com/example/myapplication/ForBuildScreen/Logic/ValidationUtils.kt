package com.example.myapplication.ForBuildScreen.Logic

import com.example.myapplication.ForBuildScreen.*

fun validateAllBlocks(blocks: List<CodeBlock>, declaredVariables: List<String>): Set<Int> {
    val errorBlocks = mutableSetOf<Int>()
    val tempVariables = mutableListOf<String>()
    val blockStack = mutableListOf<Pair<Int, BlockType>>()
    validateVariableDeclarations(blocks, errorBlocks, tempVariables)
    validateBlockStructure(blocks, errorBlocks, blockStack, declaredVariables)
    blockStack.forEach { (blockId, _) -> errorBlocks.add(blockId) }

    return errorBlocks
}

fun validateVariableDeclarations(
    blocks: List<CodeBlock>,
    errorBlocks: MutableSet<Int>,
    tempVariables: MutableList<String>
) {
    blocks.filter { it.type == BlockType.VARIABLE_DECLARATION }
        .forEach { block ->
            val varName = block.content.trim()
            when {
                !isValidVariableName(varName) -> errorBlocks.add(block.id)
                varName in tempVariables -> errorBlocks.add(block.id)
                else -> tempVariables.add(varName)
            }
        }
}

fun validateBlockStructure(
    blocks: List<CodeBlock>,
    errorBlocks: MutableSet<Int>,
    blockStack: MutableList<Pair<Int, BlockType>>,
    declaredVariables: List<String>
) {
    blocks.forEach { block ->
        // проверяем все кроме флагов
        if (block.type != BlockType.ENDIF && block.type != BlockType.ENDWHILE && block.type != BlockType.ELSE) {
            if (block.rpn.contains("ERROR")) {
                errorBlocks.add(block.id)
            }
        }

        when (block.type) {
            BlockType.IF, BlockType.WHILE -> {

                if (block.rpn.contains("ERROR")) {
                    errorBlocks.add(block.id)
                }
                blockStack.add(block.id to block.type)
            }
            BlockType.ENDIF -> handleEndBlock(block, blockStack, errorBlocks, BlockType.IF)
            BlockType.ENDWHILE -> handleEndBlock(block, blockStack, errorBlocks, BlockType.WHILE)
            else->""
        }
    }
}


fun handleEndBlock(
    block: CodeBlock,
    blockStack: MutableList<Pair<Int, BlockType>>,
    errorBlocks: MutableSet<Int>,
    expectedType: BlockType
) {
    if (blockStack.isEmpty() || blockStack.last().second != expectedType) {
        errorBlocks.add(block.id)
        if (blockStack.isNotEmpty()) errorBlocks.add(blockStack.last().first)
    } else {
        blockStack.removeAt(blockStack.size - 1)
    }
}


fun hasRpnError(block: CodeBlock, errorBlocks: Set<Int>): Boolean {
    return block.id in errorBlocks
}

