package org.example.project.SCREEN

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.project.DATA.MODELS.Note
import org.example.project.DOMAIN.CommonViewModel
import org.example.project.screen.widgets.TodoCard
import org.jetbrains.compose.resources.painterResource
import self_notron.composeapp.generated.resources.Res
import self_notron.composeapp.generated.resources.plus_svgrepo_com

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTaskScreen(viewModel: CommonViewModel) {
    // Fetch notes once when the screen is first composed
    LaunchedEffect(Unit) {
        viewModel.GetNotes()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val getNoteState by viewModel.GetFlow.collectAsState()

    // Listen for errors from all flows and show a snackbar
    LaunchedEffect(Unit) {
        launch {
            viewModel.AddStateFlow.collectLatest { state ->
                if (state.error != null) snackbarHostState.showSnackbar("Error adding note: ${state.error}")
            }
        }
        launch {
            viewModel.DeleteFlow.collectLatest { state ->
                if (state.error != null) snackbarHostState.showSnackbar("Error deleting note: ${state.error}")
            }
        }
        launch {
            viewModel.UpdateFlow.collectLatest { state ->
                if (state.error != null) snackbarHostState.showSnackbar("Error updating note: ${state.error}")
            }
        }
        launch {
            viewModel.GetFlow.collectLatest { state ->
                if (state.error != null) snackbarHostState.showSnackbar("Error fetching notes: ${state.error}")
            }
        }
    }


    Scaffold(
        topBar = { TopAppBar(title = { Text("All Tasks") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Create a new note with default non-null values to prevent crashes
                val newNote = Note(
                    Task = "New Task",
                    Checked = false,
                    Priority = 1
                )
                viewModel.AddNote(newNote)
            }) {
                Text("add")
                //Icon(painter = painterResource(Res.drawable.plus_svgrepo_com), contentDescription = "Add Task")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                getNoteState.isLoading -> {
                    CircularProgressIndicator()
                }
                getNoteState.Sucess != null -> {
                    val notes = getNoteState.Sucess ?: emptyList()
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(notes) { note ->
                            TodoCard(viewModel = viewModel, note = note)
                        }
                    }
                }
                // Error state is handled by the LaunchedEffect above,
                // but you could show a message here too if you wanted.
                else -> {
                    // This could be a place for an empty state message
                     Text("No tasks yet. Add one!")
                }
            }
        }
    }
}