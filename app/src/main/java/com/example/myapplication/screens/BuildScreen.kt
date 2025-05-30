package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.otherElements.Bar
import com.example.myapplication.ForBuildScreen.*
import com.example.myapplication.ForBuildScreen.Logic.*
import com.example.myapplication.ScriptSaving.CustomScriptRepository
import com.example.myapplication.ScriptSaving.Script
import com.example.myapplication.interpretor.main.Interpretor
import com.example.myapplication.otherElements.RunButton
import com.example.myapplication.otherElements.SetUserScriptButton

@Composable
fun BuildScreen(navController: NavHostController) {
    var blocks by remember { mutableStateOf(listOf(CodeBlock(1))) }
    var showBlockDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedBlockId by remember { mutableStateOf<Int?>(null) }
    var consoleVisibility by remember { mutableStateOf(false) }
    var currentEditText by remember { mutableStateOf("") }
    var declaredVariables by remember { mutableStateOf(listOf<String>()) }
    var errorBlocks by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var rpnTokenList by remember { mutableStateOf(emptyList<String>()) }
    var consoleOutput by remember { mutableStateOf(listOf<String>()) }
    val interpreter = remember { Interpretor() }
    var showSaveDialog by remember { mutableStateOf(false) }

    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    LaunchedEffect(declaredVariables) {
        blocks = blocks.map { block ->
            when (block.type) {
                BlockType.VARIABLE_DECLARATION -> block // пропускаю объявления
                else -> block.copy(rpn = generateRpn(block.type, block.content, declaredVariables))
            }
        }
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = navBarPadding.calculateBottomPadding())
    ) {
        Bar(navController)

        // Показываем сообщение об ошибках, если они есть
        if (errorBlocks.isNotEmpty()) {
            Text(
                text = "Ошибки в коде! Исправьте отмеченные блоки перед запуском.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(blocks, key = { it.id }) { block ->
                when (block.type) {
                    BlockType.PLACEHOLDER -> PlaceholderBlock(
                        onClick = {
                            selectedBlockId = block.id
                            showBlockDialog = true
                        }
                    )
                    else -> FilledBlock(
                        block = block,
                        hasError = hasRpnError(block, errorBlocks),
                        onEdit = {
                            selectedBlockId = block.id
                            currentEditText = block.content
                            showEditDialog = true
                        },
                        onDelete = {
                            if (block.type == BlockType.VARIABLE_DECLARATION) {
                                declaredVariables = declaredVariables - block.content.trim()
                            }
                            blocks = blocks.filter { it.id != block.id }
                            errorBlocks = errorBlocks - block.id
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            RunButton(
                onClick = {
                    errorBlocks = validateAllBlocks(blocks, declaredVariables)

                    if (errorBlocks.isEmpty()) {
                        // строки в списки токенов
                        val rpnTokens = blocks
                            .filter { it.type != BlockType.PLACEHOLDER }
                            .flatMap { block ->
                                block.rpn.split(" ").filter { it.isNotBlank() }
                            }
                        println("RPN TOKENS: ${rpnTokens.joinToString(" ")}") // Лог в консоль
                        consoleVisibility = true
                        rpnTokenList = rpnTokens
                        val output = interpreter.run(rpnTokens)
                        consoleOutput = output
                    }
                }
            )
        }
    }

    // Диалог выбора типа блока
    if (showBlockDialog) {
        AlertDialog(
            onDismissRequest = { showBlockDialog = false },
            title = { Text("Select Block Type") },
            text = {
                Column {
                    BlockType.values()
                        .filter { it != BlockType.PLACEHOLDER }
                        .forEach { type ->
                            Button(
                                onClick = {
                                    blocks = blocks.map { block ->
                                        if (block.id == selectedBlockId) {
                                            val defaultContent = when (type) {
                                                BlockType.VARIABLE_DECLARATION -> "x"
                                                BlockType.ASSIGNMENT -> "x = 0"
                                                BlockType.IF -> "x > 0"
                                                BlockType.WHILE -> "x < 10"
                                                BlockType.PRINT -> "x"
                                                else -> ""

                                            }
                                            val newBlock = block.copy(
                                                type = type,
                                                content = defaultContent,
                                                rpn = generateRpn(type, defaultContent, declaredVariables)
                                            )

                                            // Для объявления переменной проверяем перед добавлением
                                            if (type == BlockType.VARIABLE_DECLARATION) {
                                                val varName = defaultContent.trim()
                                                if (varName.matches(Regex("[a-zA-Z_]\\w*")) &&
                                                    varName !in declaredVariables) {
                                                    declaredVariables = declaredVariables + varName
                                                }
                                            }

                                            newBlock
                                        } else block
                                    }
                                    blocks = blocks + CodeBlock(blocks.maxOf { it.id } + 1)
                                    showBlockDialog = false
                                }
                            )
                            {
                                Text(type.name)
                            }
                        }

                }
            },
            confirmButton = {}
        )
    }

    // Диалог редактирования блока
    if (showEditDialog) {
        val currentBlock = blocks.firstOrNull { it.id == selectedBlockId }
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit ${currentBlock?.type} Block") },
            text = {
                Column {
                    TextField(
                        value = currentEditText,
                        onValueChange = { currentEditText = it },
                        label = { Text("Enter content") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Подсказки для разных типов блоков
                    when (currentBlock?.type) {
                        BlockType.VARIABLE_DECLARATION -> {
                            Text("Format: variable_name", style = MaterialTheme.typography.labelSmall)
                            Text("Example: counter", style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.ASSIGNMENT -> {
                            Text("Format: variable = expression", style = MaterialTheme.typography.labelSmall)
                            Text("Example: x = 5 + y", style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.IF -> {
                            Text("Format: condition", style = MaterialTheme.typography.labelSmall)
                            Text("Example: x > 0 ", style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.WHILE -> {
                            Text("Format: condition", style = MaterialTheme.typography.labelSmall)
                            Text("Example: i < variable or constant or expression", style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.PRINT -> {
                            Text("Format: variable, expression or 'string'", style = MaterialTheme.typography.labelSmall)
                            Text("Examples: x, x + 5, 'Hello!'", style = MaterialTheme.typography.labelSmall)
                        }
                        else -> {}
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Временная переменная для новых declaredVariables
                        val newDeclaredVariables = if (currentBlock?.type == BlockType.VARIABLE_DECLARATION) {
                            declaredVariables - currentBlock.content.trim() + currentEditText.trim()
                        } else {
                            declaredVariables
                        }

                        // Сначала обновляем все блоки с новыми declaredVariables
                        blocks = blocks.map { block ->
                            if (block.id == selectedBlockId) {
                                block.copy(
                                    content = currentEditText,
                                    rpn = generateRpn(block.type, currentEditText, newDeclaredVariables)
                                )
                            } else {
                                when (block.type) {
                                    BlockType.VARIABLE_DECLARATION -> block
                                    else -> block.copy(rpn = generateRpn(block.type, block.content, newDeclaredVariables))
                                }
                            }
                        }

                        // Затем обновляем declaredVariables
                        if (currentBlock?.type == BlockType.VARIABLE_DECLARATION) {
                            declaredVariables = newDeclaredVariables
                        }

                        showEditDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Поле с сохранением скрипта
    if (showSaveDialog) {
        SetUserScriptButton(
            onDismiss = { showSaveDialog = false},
            onSave = { name, desc ->
                CustomScriptRepository.scripts.add(Script(name, desc, blocks))
                showSaveDialog = false
            }
        )
    }

    // Консоль вывода
    if (consoleVisibility) {
        OutputConsoleScreen(
            onClose = { consoleVisibility = false },
            content = consoleOutput,
            showSaveDialog = showSaveDialog,
            onShowSaveDialogChange = { showSaveDialog = it}
        )
    }
}