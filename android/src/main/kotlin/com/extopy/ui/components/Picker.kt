package com.extopy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun <T> Picker(
    modifier: Modifier = Modifier,
    items: Map<T, String>,
    selected: T,
    placeholder: String = "",
    onSelected: (T) -> Unit,
) {

    var showDialog by remember { mutableStateOf(false) }

    OutlinedTextField(
        leadingIcon = {
            val selectedItem = items[selected]
            Text(
                text = selectedItem ?: placeholder,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = if (selectedItem != null) MaterialTheme.colorScheme.onSurface
                    else Color.LightGray
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clickable {
                        showDialog = true
                    }
            )
        },
        value = "",
        onValueChange = {},
        modifier = modifier
    )

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 40.dp)
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surface
                    )
            ) {
                items(items.toList()) { item ->
                    Text(
                        text = item.second,
                        modifier = Modifier
                            .clickable {
                                onSelected(item.first)
                                showDialog = false
                            }
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
            }
        }
    }

}
