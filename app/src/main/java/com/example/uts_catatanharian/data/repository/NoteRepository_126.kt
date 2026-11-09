package com.example.uts_catatanharian.data.repository

import com.example.uts_catatanharian.data.dao.NoteDao_126
import com.example.uts_catatanharian.data.entity.NoteEntity_126
import kotlinx.coroutines.flow.Flow

class NoteRepository_126(private val noteDao: NoteDao_126) {
    suspend fun insertNote_126(note: NoteEntity_126): Long {
        return noteDao.insertNote_126(note)
    }

    suspend fun updateNote_126(note: NoteEntity_126): Int {
        return noteDao.updateNote_126(note)
    }

    suspend fun deleteNote_126(noteId: Long, userId: Long): Int {
        return noteDao.deleteNoteById_126(noteId, userId)
    }

    fun getAllNotes_126(userId: Long): Flow<List<NoteEntity_126>> {
        return noteDao.getAllNotesByUser_126(userId)
    }

    suspend fun getNoteById_126(noteId: Long, userId: Long): NoteEntity_126? {
        return noteDao.getNoteByIdAndUser_126(noteId, userId)
    }

    suspend fun getNoteCount_126(userId: Long): Int {
        return noteDao.getNoteCountByUser_126(userId)
    }
}
