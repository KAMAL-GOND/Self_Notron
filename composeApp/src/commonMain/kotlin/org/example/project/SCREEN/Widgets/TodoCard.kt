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
import org.example.project.SCREEN.Widgets.Logd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(viewModel: CommonViewModel, note: Note) {
    var isEditable by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(note.Checked ?: false) }
    var text by remember { mutableStateOf(note.Task ?: "") }



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp).combinedClickable(
                onClick = {
                   // isEditable = !isEditable
                },
                onLongClick = {
                    isEditable = true
                },
                onDoubleClick = {
                    isEditable = true

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
            //var a = note.Checked ?: false
            Checkbox(

                checked = isChecked,
                onCheckedChange = { it ->
                    Logd("Checkbox", it.toString())
//                    if(it==true){
//
//                    }
//                    isChecked = it
                    Logd("isChecked", isChecked.toString())
                    //var a = it

                    note.Checked = it
                    Logd("note.Checked", note.Checked.toString())
                    viewModel.UpdateNote(note)
                    isChecked = it
                    Logd("isChecked 2", isChecked.toString())
                    //note.Checked = !note.Checked!!
                }
            )

            if (isEditable) {
                TextField(
                    value = text,
                    onValueChange = { updatedText ->
                        text = updatedText
                        note.Task = updatedText.toString()
                        //viewModel.UpdateNote(note.copy(Task = updatedText))
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
                    viewModel.UpdateNote(note)
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