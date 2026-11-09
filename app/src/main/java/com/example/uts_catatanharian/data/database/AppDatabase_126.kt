package com.example.uts_catatanharian.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uts_catatanharian.data.dao.NoteDao_126
import com.example.uts_catatanharian.data.dao.UserDao_126
import com.example.uts_catatanharian.data.entity.NoteEntity_126
import com.example.uts_catatanharian.data.entity.UserEntity_126

@Database(entities = [UserEntity_126::class, NoteEntity_126::class], version = 1, exportSchema = false)
abstract class AppDatabase_126 : RoomDatabase() {
    abstract fun userDao_126(): UserDao_126
    abstract fun noteDao_126(): NoteDao_126

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase_126? = null

        fun getDatabase_126(context: Context): AppDatabase_126 {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase_126::class.java,
                    "catatan_harian_db_126"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
