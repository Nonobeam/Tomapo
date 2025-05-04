package per.nonobeam.tomapo.app.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.launch

@Composable
fun TodoMainScreen(modifier: Modifier = Modifier) {
    val todoItems = remember { mutableStateListOf<String>().apply {
    }}
    var newItemText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val items = remember { derivedStateOf { todoItems.toList() } }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Todo List",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            itemsIndexed(
                items = items.value,
                key = { index, _ -> index }
            ) { index, item ->
                key(index) {
                    TodoItem(
                        initialText = item,
                        index = index,
                        isLast = false,
                        onItemChanged = { newValue ->
                            todoItems[index] = newValue
                        },
                        onDelete = {
                            todoItems.removeAt(index)
                        }
                    )
                }
            }

            item {
                TodoItem(
                    initialText = newItemText,
                    index = todoItems.size,
                    isLast = true,
                    onItemChanged = { newValue ->
                        newItemText = newValue
                    },
                    onItemSubmitted = {
                        if (newItemText.isNotBlank()) {
                            todoItems.add(newItemText)
                            newItemText = ""
                            coroutineScope.launch {
                                listState.animateScrollToItem(todoItems.size)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    initialText: String,
    index: Int,
    isLast: Boolean,
    onItemChanged: (String) -> Unit,
    onItemSubmitted: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    var text by remember { mutableStateOf(initialText) }
    val isBeingDragged by remember { mutableStateOf(false) }

    LaunchedEffect(initialText) {
        text = initialText
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(
                if (isBeingDragged) Color.LightGray.copy(alpha = 0.5f)
                else Color.Transparent
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isLast) "+" else "${index + 1}.",
            modifier = Modifier.width(40.dp),
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = text,
            onValueChange = {
                text = it
                onItemChanged(it)
            },
            placeholder = {
                Text(text = if (isLast) "Add new task" else "Task ${index + 1}")
            },
            singleLine = true,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { onItemSubmitted?.invoke() }
            )
        )

        if (!isLast) {
            IconButton(onClick = { onDelete?.invoke() }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}