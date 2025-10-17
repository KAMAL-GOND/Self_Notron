package org.example.project.screen.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding


import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.example.project.DATA.MODELS.Note
import org.example.project.DOMAIN.CommonViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import self_notron.composeapp.generated.resources.Res
import self_notron.composeapp.generated.resources.icons8_delete
import self_notron.composeapp.generated.resources.tick_svgrepo_com

@Composable
fun TodoCard(viewModel: CommonViewModel, note: Note) {
    var isEditable by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f)
            .background(color = Color.Red, shape = RectangleShape)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    note.Checked = !note.Checked
                    viewModel.UpdateNote(note)
                },
                onLongClick = {
                    isEditable = !isEditable
                },
                onDoubleClick = {
                    isEditable = !isEditable
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(note.Checked, onCheckedChange = {
            note.Checked = !note.Checked
            viewModel.UpdateNote(note)
        })
        if (note.Checked) {
            TextField(
                value = note.Task.toString(),
                onValueChange = { note.Task = it },
                readOnly = isEditable,
                modifier = Modifier.fillMaxWidth(0.7f),
                textStyle = TextStyle(
                    textDecoration = TextDecoration.LineThrough
                )
            )
        } else {
            TextField(
                value = note.Task.toString(),
                onValueChange = { note.Task = it },
                readOnly = isEditable,
                modifier = Modifier.fillMaxWidth(0.7f),
                textStyle = TextStyle(
                    textDecoration = TextDecoration.Underline
                )
            )
        }
        IconButton(onClick = {
            if (isEditable) {
                viewModel.UpdateNote(note)
                isEditable = false
            } else {
                viewModel.DeleteNote(note.id.toString())
            }
        }) {
            if (isEditable) {
                Icon(painter = painterResource(Res.drawable.tick_svgrepo_com), contentDescription = "Edit")
            } else {
                Icon(painter = painterResource(Res.drawable.icons8_delete), contentDescription = "Delete")
            }
        }
    }
}

@Preview
@Composable
fun TodoCard_Checked_Preview() {
    val fakeViewModel = CommonViewModel()
    val note = Note(
        id = 1,
        Task = "This is a completed task",
        Checked = true
    )
    Surface {
        TodoCard(viewModel = fakeViewModel, note = note)
    }
}

@Preview
@Composable
fun TodoCard_Unchecked_Preview() {
    val fakeViewModel = CommonViewModel()
    val note = Note(
        id = 2,
        Task = "This is an active task",
        Checked = false
    )
    Surface {
        TodoCard(viewModel = fakeViewModel, note = note)
    }
}

@Preview
@Composable
fun TodoCard_LongText_Preview() {
    val fakeViewModel = CommonViewModel()
    val note = Note(
        id = 3,
        Task = "This is a very very very very very long task to see how it behaves when the text overflows the available space.",
        Checked = false
    )
    Surface {
        TodoCard(viewModel = fakeViewModel, note = note)
    }
}
