package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(title: String, content: String): Result<Unit> {
        if (title.isBlank()) return Result.failure(IllegalArgumentException("Заголовок для элемента пуст"))

        val note = Note(
            id = 0,
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )

        return repository.addNote(note)
    }
}