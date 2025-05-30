package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.otherElements.Bar
import com.example.myapplication.ForBuildScreen.*
import com.example.myapplication.ForBuildScreen.Logic.*
import com.example.myapplication.R
import com.example.myapplication.ScriptSaving.CustomScriptRepository
import com.example.myapplication.ScriptSaving.Script
import com.example.myapplication.ScriptSaving.ScriptStorage
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

    val context = LocalContext.current
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()
    val colors = MaterialTheme.colorScheme

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
            .background(colors.background)
    ) {
        Bar(navController)

        // Показываем сообщение об ошибках, если они есть
        if (errorBlocks.isNotEmpty()) {
            Text(
                text = stringResource(R.string.error_run_conflict),
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
                        println("RPN TOKENS: ${rpnTokens.joinToString(" ")}")
                        consoleVisibility = true
                        rpnTokenList = rpnTokens
                        val testrpn = listOf("arr","2","init[]","arr","0","1","[]=","arr","1","2","[]=","arr","0","[]","print")
                        val output = interpreter.run(testrpn)
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
            title = { Text(stringResource(R.string.select_block_type)) },
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
                                                BlockType.VARIABLE_DECLARATION -> ""
                                                BlockType.ARRAY_DECLARATION ->""
                                                BlockType.ASSIGNMENT -> "x = 0"
                                                BlockType.IF -> "x > 0"
                                                BlockType.WHILE -> "x < 10"
                                                BlockType.PRINT -> "x"
                                                else -> ""
                                            }
                                            val newBlock = block.copy(
                                                type = type,
                                                content = defaultContent,
                                                rpn = generateRpn(
                                                    type,
                                                    defaultContent,
                                                    declaredVariables
                                                )
                                            )

                                            //Для объявления переменной проверяем перед добавлением
                                            if (type == BlockType.VARIABLE_DECLARATION) {
                                                val varName = defaultContent.trim()
                                                if (varName.matches(Regex("[a-zA-Z_]\\w*"))
                                                    && varName !in declaredVariables) {
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
                        label = { Text(stringResource(R.string.enter_content)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Подсказки для разных типов блоков
                    when (currentBlock?.type) {
                        BlockType.VARIABLE_DECLARATION -> {
                            Text(stringResource(R.string.help_formar_variable_declaration),
                                style = MaterialTheme.typography.labelSmall)
                            Text(stringResource(R.string.help_example_variable_declaration),
                                style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.ASSIGNMENT -> {
                            Text(stringResource(R.string.help_formar_assignment),
                                style = MaterialTheme.typography.labelSmall)
                            Text(stringResource(R.string.help_example_assignment),
                                style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.IF -> {
                            Text(stringResource(R.string.help_formar_if),
                                style = MaterialTheme.typography.labelSmall)
                            Text(stringResource(R.string.help_example_if),
                                style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.WHILE -> {
                            Text(stringResource(R.string.help_formar_while),
                                style = MaterialTheme.typography.labelSmall)
                            Text(stringResource(R.string.help_example_while),
                                style = MaterialTheme.typography.labelSmall)
                        }
                        BlockType.PRINT -> {
                            Text(stringResource(R.string.help_formar_print),
                                style = MaterialTheme.typography.labelSmall)
                            Text(stringResource(R.string.help_example_print),
                                style = MaterialTheme.typography.labelSmall)
                        }
                        else -> {}
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Временная переменная для новых declaredVariables
                        val newDeclaredVariables =
                            if (currentBlock?.type == BlockType.VARIABLE_DECLARATION) {
                            declaredVariables - currentBlock.content.trim() + currentEditText.trim()
                        } else {
                            declaredVariables
                        }

                        // Сначала обновляем все блоки с новыми declaredVariables
                        blocks = blocks.map { block ->
                            if (block.id == selectedBlockId) {
                                block.copy(
                                    content = currentEditText,
                                    rpn = generateRpn(
                                        block.type,
                                        currentEditText,
                                        newDeclaredVariables
                                    )
                                )
                            } else {
                                when (block.type) {
                                    BlockType.VARIABLE_DECLARATION -> block
                                    else -> block.copy(
                                        rpn = generateRpn(
                                            block.type,
                                            block.content,
                                            newDeclaredVariables
                                        )
                                    )
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
                    Text(stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text(stringResource(R.string.cancel))
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
                ScriptStorage.saveScripts(context, CustomScriptRepository.scripts)
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