package com.example.uts_catatanharian.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.uts_catatanharian.data.entity.NoteEntity_126
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao_126 {
    @Insert
    suspend fun insertNote_126(note: NoteEntity_126): Long

    @Update
    suspend fun updateNote_126(note: NoteEntity_126): Int

    @Query("DELETE FROM notes_126 WHERE id = :noteId AND user_id = :userId")
    suspend fun deleteNoteById_126(noteId: Long, userId: Long): Int

    @Query("SELECT * FROM notes_126 WHERE user_id = :userId ORDER BY tanggal DESC")
    fun getAllNotesByUser_126(userId: Long): Flow<List<NoteEntity_126>>

    @Query("SELECT * FROM notes_126 WHERE id = :noteId AND user_id = :userId LIMIT 1")
    suspend fun getNoteByIdAndUser_126(noteId: Long, userId: Long): NoteEntity_126?

    @Query("SELECT COUNT(*) FROM notes_126 WHERE user_id = :userId")
    suspend fun getNoteCountByUser_126(userId: Long): Int
}
