package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.NoteDao
import com.example.myapplication.data.local.mapper.toDomain
import com.example.myapplication.data.local.mapper.toEntity
import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first


class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override suspend fun getNotes(): Result<List<Note>> {
        return try {
            val list = dao.getAll().first().map { it.toDomain() }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun addNote(note: Note): Result<Unit> {
        return try {
            dao.insert(note.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun deleteNote(id: Long): Result<Unit> {
        return try {
            val entity = dao.getById(id)
            if (entity != null) {
                dao.delete(entity)
                Result.success(Unit)
            } else {
                Result.failure(Exception("заметка не найдена :("))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}