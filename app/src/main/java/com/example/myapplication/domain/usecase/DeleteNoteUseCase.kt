package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Long): Result<Unit> {
        return repository.deleteNote(id)
    }
}