package com.example.uts_catatanharian.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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
import com.example.uts_catatanharian.ui.auth.LoginActivity_126
import com.example.uts_catatanharian.ui.notes.AddNoteActivity_126
import com.example.uts_catatanharian.ui.notes.EditNoteActivity_126
import com.example.uts_catatanharian.ui.notes.ListNoteActivity_126
import com.example.uts_catatanharian.utils.SessionManager_126
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity_126 : AppCompatActivity() {

    private lateinit var noteRepository: NoteRepository_126
    private lateinit var sessionManager: SessionManager_126
    private lateinit var noteAdapter: NoteAdapter_126

    private var currentUserId: Long = -1L

    private lateinit var tvGreeting: TextView
    private lateinit var tvNoteCount: TextView
    private lateinit var tvEmptyState: TextView
    private lateinit var rvRecentNotes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sessionManager = SessionManager_126(this)
        if (!sessionManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity_126::class.java))
            finish()
            return
        }

        currentUserId = sessionManager.getUserId()

        val noteDao = AppDatabase_126.getDatabase_126(this).noteDao_126()
        noteRepository = NoteRepository_126(noteDao)

        tvGreeting = findViewById(R.id.tv_greeting_126)
        tvNoteCount = findViewById(R.id.tv_note_count_126)
        tvEmptyState = findViewById(R.id.tv_empty_state_126)
        rvRecentNotes = findViewById(R.id.rv_recent_notes_126)

        val username = sessionManager.getUsername() ?: "Pengguna"
        tvGreeting.text = "Halo, $username"

        val btnLogout = findViewById<ImageButton>(R.id.btn_logout_126)
        btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        val btnAddNote = findViewById<MaterialButton>(R.id.btn_add_note_126)
        btnAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity_126::class.java)
            startActivity(intent)
        }

        val tvViewAll = findViewById<TextView>(R.id.tv_view_all_126)
        tvViewAll.setOnClickListener {
            val intent = Intent(this, ListNoteActivity_126::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvRecentNotes.layoutManager = LinearLayoutManager(this)
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
        rvRecentNotes.adapter = noteAdapter
    }

    override fun onResume() {
        super.onResume()
        loadDashboardData()
    }

    private fun loadDashboardData() {
        CoroutineScope(Dispatchers.IO).launch {
            noteRepository.getAllNotes_126(currentUserId).collect { notes ->
                withContext(Dispatchers.Main) {
                    tvNoteCount.text = notes.size.toString()
                    noteAdapter.updateNotes_126(notes)

                    if (notes.isEmpty()) {
                        tvEmptyState.visibility = View.VISIBLE
                        rvRecentNotes.visibility = View.GONE
                    } else {
                        tvEmptyState.visibility = View.GONE
                        rvRecentNotes.visibility = View.VISIBLE
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
                        Toast.makeText(this@DashboardActivity_126, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
                        loadDashboardData()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                sessionManager.logoutUser()
                Toast.makeText(this, "Berhasil logout", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity_126::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
