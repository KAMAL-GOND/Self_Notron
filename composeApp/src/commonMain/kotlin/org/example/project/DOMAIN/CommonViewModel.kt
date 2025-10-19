package org.example.project.DOMAIN

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.example.project.DATA.DI.Supabase
import org.example.project.DATA.MODELS.Note
import org.example.project.SCREEN.Widgets.Logd

class CommonViewModel : ViewModel() {
    private val _AddStateFlow = MutableStateFlow(State())
    val AddStateFlow = _AddStateFlow.asStateFlow()

    var Client = Supabase.Client

     fun AddNote(note: Note) =
         viewModelScope.launch {
        _AddStateFlow.value = State(isLoading = true)
        try{
            val response = Client.from("Self_Notron").insert(note)
           // _AddStateFlow.value = State(Sucess = response)
            GetNotes()


        }
        catch (e: Exception) {
            _AddStateFlow.value = State(error = e.message)
        }

    }

    private val _DeleteFlow = MutableStateFlow(State())
    val DeleteFlow = _DeleteFlow.asStateFlow()
    fun DeleteNote(id: String) =
        viewModelScope.launch {
            _DeleteFlow.value = State(isLoading = true)
            try{
                 Client.from("Self_Notron").delete {
                     filter {
                    eq("id", id)}
                }
                //_DeleteFlow.value = State(Sucess = "Deleted")
                GetNotes()


            }
            catch (e: Exception) {
                _DeleteFlow.value = State(error = e.message)
            }

        }

    private val _UpdateFlow = MutableStateFlow(State())
    val UpdateFlow = _UpdateFlow.asStateFlow()
    fun UpdateNote(note: Note) =
        viewModelScope.launch {
            _UpdateFlow.value = State(isLoading = true)
            try{
                val response = Client.from("Self_Notron").update(note) {
                    filter {
                    eq("id", note.id.toString())}
                }
                //_UpdateFlow.value = State(Sucess = response)
                GetNotes()


            }
            catch (e: Exception) {
                _UpdateFlow.value = State(error = e.message)
            }

        }

    private val _GetFlow = MutableStateFlow(State())
    val GetFlow = _GetFlow.asStateFlow()
    fun GetNotes() =
        viewModelScope.launch {
            _GetFlow.value = State(isLoading = true)
            try{
                val response1 = Client.from("Self_Notron").select()
                Logd("response1",response1.toString())
               val response = response1.decodeList<Note>()
                //val response = Client.from("Self_Notron").select().decodeAs<Note>()
                //Logd("response", response.toString())


                _GetFlow.value = State(Sucess = response , isLoading = false)

            }
            catch (e: Exception) {
                _GetFlow.value = State(error = e.message)
            }

        }

    fun ClearState() {
        _AddStateFlow.value = State()
        _DeleteFlow.value = State()
        _UpdateFlow.value = State()
        _GetFlow.value = State()
    }






}
//@Serializable
data class State(
    var isLoading: Boolean = false,
    var Sucess: List<Note>? = null,
    var error: String? = null,

    )