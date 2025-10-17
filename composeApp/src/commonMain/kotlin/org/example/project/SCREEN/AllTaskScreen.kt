package org.example.project.SCREEN

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.example.project.DATA.MODELS.Note
import org.example.project.DOMAIN.CommonViewModel
import org.example.project.screen.widgets.TodoCard
import org.jetbrains.compose.resources.painterResource
import self_notron.composeapp.generated.resources.Res
import self_notron.composeapp.generated.resources.plus_svgrepo_com

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTaskScreen( veiwModel: CommonViewModel) {
    LaunchedEffect(Unit){
        veiwModel.GetNotes()

    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var AddNoteState= veiwModel.AddStateFlow.collectAsState()
    var DeleteNoteState = veiwModel.DeleteFlow.collectAsState()
    var UpdateNoteState = veiwModel.UpdateFlow.collectAsState()
    var GetNoteState = veiwModel.GetFlow.collectAsState()


    Scaffold(
        topBar = {Text("All Tasks")},
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.background(shape = CircleShape, color = Color.Cyan),onClick = {
                veiwModel.AddNote(Note())
            }){
                Text("Add Task")
                Icon(painter = painterResource(Res.drawable.plus_svgrepo_com), contentDescription = "Add Task")

            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        //snackbarHost = { Snackbar(snackbarData = TODO()) },




    ) {
        if(AddNoteState.value.isLoading){ Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){CircularProgressIndicator()}}
        else if(AddNoteState.value.error != null){
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Error: ${AddNoteState.value.error}"
                )
            }





        }
        else if(AddNoteState.value.Sucess != null){
            LazyColumn {
                items(AddNoteState.value.Sucess!! as List<Note>){note->
                    TodoCard(viewModel = veiwModel, note = note)

                }
                if(DeleteNoteState.value.error != null || UpdateNoteState.value.error != null || GetNoteState.value.error != null){
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Error: ${DeleteNoteState.value.error ?: UpdateNoteState.value.error ?: GetNoteState.value.error}"
                        )

                    }
                }
            }
        }



    }
}
@Composable
fun TopBar(){

}