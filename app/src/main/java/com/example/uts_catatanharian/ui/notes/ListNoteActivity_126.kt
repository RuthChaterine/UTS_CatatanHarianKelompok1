package com.example.uts_catatanharian.ui.notes

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.adapter.NoteAdapter_126
import com.example.uts_catatanharian.data.database.AppDatabase_126
import com.example.uts_catatanharian.data.repository.NoteRepository_126
import com.example.uts_catatanharian.utils.SessionManager_126
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListNoteActivity_126 : AppCompatActivity() {

    private lateinit var noteRepository: NoteRepository_126
    private lateinit var sessionManager: SessionManager_126
    private lateinit var noteAdapter: NoteAdapter_126

    private var currentUserId: Long = -1L
    private lateinit var rvAllNotes: RecyclerView
    private lateinit var tvEmptyList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_note)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_126)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Semua Catatan"

        toolbar.setNavigationOnClickListener { finish() }

        sessionManager = SessionManager_126(this)
        currentUserId = sessionManager.getUserId()

        val noteDao = AppDatabase_126.getDatabase_126(this).noteDao_126()
        noteRepository = NoteRepository_126(noteDao)

        rvAllNotes = findViewById(R.id.rv_all_notes_126)
        tvEmptyList = findViewById(R.id.tv_empty_list_126)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvAllNotes.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter_126(
            emptyList(),
            onEditClick = { note ->
                val intent = Intent(this, EditNoteActivity_126::class.java).apply {
                    putExtra("NOTE_ID", note.id)
                }
                startActivity(intent)
            },
            onDeleteClick = { note ->
                showDeleteConfirmationDialog(note.id)
            }
        )
        rvAllNotes.adapter = noteAdapter
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            noteRepository.getAllNotes_126(currentUserId).collect { notes ->
                withContext(Dispatchers.Main) {
                    noteAdapter.updateNotes_126(notes)
                    if (notes.isEmpty()) {
                        tvEmptyList.visibility = android.view.View.VISIBLE
                        rvAllNotes.visibility = android.view.View.GONE
                    } else {
                        tvEmptyList.visibility = android.view.View.GONE
                        rvAllNotes.visibility = android.view.View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(noteId: Long) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Catatan")
            .setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
            .setPositiveButton("Hapus") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    noteRepository.deleteNote_126(noteId, currentUserId)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ListNoteActivity_126, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
                        loadNotes()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
