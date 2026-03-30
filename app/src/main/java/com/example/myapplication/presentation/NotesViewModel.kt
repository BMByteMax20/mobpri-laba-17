package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.usecase.AddNoteUseCase
import com.example.myapplication.domain.usecase.DeleteNoteUseCase
import com.example.myapplication.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadNotes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = getNotesUseCase()
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(notes = (result.getOrNull() ?: emptyList()) as List<Note>, isLoading = false)
                }
            } else {
                _uiState.update {
                    it.copy(error = result.exceptionOrNull()?.message, isLoading = false)
                }
            }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = addNoteUseCase(title, content)
            if (result.isSuccess) {
                loadNotes()
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(error = result.exceptionOrNull()?.message, isLoading = false)
                }
            }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = deleteNoteUseCase(id)
            if (result.isSuccess) {
                loadNotes()
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(error = result.exceptionOrNull()?.message, isLoading = false)
                }
            }
        }
    }
}