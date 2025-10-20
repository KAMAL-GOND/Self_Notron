package org.example.project.screen.widgets

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.example.project.DATA.MODELS.Note
import org.example.project.DOMAIN.CommonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(viewModel: CommonViewModel, note: Note) {
    var isEditable by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp).combinedClickable(
                onClick = {
                    isEditable = !isEditable
                },
                onLongClick = {
                    isEditable = true
                },
                onDoubleClick = {

                }

            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = note.Checked ?: false,
                onCheckedChange = { isChecked ->
                    viewModel.UpdateNote(note.copy(Checked = isChecked))
                }
            )

            if (isEditable) {
                TextField(
                    value = note.Task ?: "",
                    onValueChange = { updatedText ->
                        viewModel.UpdateNote(note.copy(Task = updatedText))
                    },
                    modifier = Modifier.weight(1f)
                )
            } else {
                Text(
                    text = note.Task ?: "",
                    modifier = Modifier.weight(1f),
                    style = if (note.Checked == true) {
                        TextStyle(textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle.Default
                    }
                )
            }

            IconButton(onClick = {
                if (isEditable) {
                    isEditable = false
                    // The viewmodel is already updated by onValueChange, so we just exit edit mode
                } else {
                    viewModel.DeleteNote(note.id)
                }
            }) {
                Text(
                     if (isEditable) "done" else "delete"
                    //contentDescription = if (isEditable) "Done" else "Delete"
                )
            }
        }
    }
}