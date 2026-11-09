package com.example.uts_catatanharian.ui.notes

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.data.database.AppDatabase_126
import com.example.uts_catatanharian.data.entity.NoteEntity_126
import com.example.uts_catatanharian.data.repository.NoteRepository_126
import com.example.uts_catatanharian.utils.SessionManager_126
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditNoteActivity_126 : AppCompatActivity() {

    private lateinit var noteRepository: NoteRepository_126
    private lateinit var sessionManager: SessionManager_126
    private var currentUserId: Long = -1L
    private var noteId: Long = -1L
    private var currentNote: NoteEntity_126? = null

    private lateinit var etTitle: TextInputEditText
    private lateinit var etDate: TextInputEditText
    private lateinit var etContent: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_edit_126)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Catatan"
        toolbar.setNavigationOnClickListener { finish() }

        sessionManager = SessionManager_126(this)
        currentUserId = sessionManager.getUserId()
        noteId = intent.getLongExtra("NOTE_ID", -1L)

        val noteDao = AppDatabase_126.getDatabase_126(this).noteDao_126()
        noteRepository = NoteRepository_126(noteDao)

        etTitle = findViewById(R.id.et_edit_title_126)
        etDate = findViewById(R.id.et_edit_date_126)
        etContent = findViewById(R.id.et_edit_content_126)

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        etDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    etDate.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        loadNoteData()

        val btnUpdate = findViewById<MaterialButton>(R.id.btn_update_126)
        btnUpdate.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val date = etDate.text.toString().trim()
            val content = etContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Judul dan isi catatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            currentNote?.let { note ->
                val updatedNote = note.copy(
                    judul = title,
                    isi = content,
                    tanggal = date
                )

                CoroutineScope(Dispatchers.IO).launch {
                    noteRepository.updateNote_126(updatedNote)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditNoteActivity_126, "Catatan berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    private fun loadNoteData() {
        if (noteId == -1L) {
            Toast.makeText(this, "Catatan tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val note = noteRepository.getNoteById_126(noteId, currentUserId)
            withContext(Dispatchers.Main) {
                if (note != null) {
                    currentNote = note
                    etTitle.setText(note.judul)
                    etDate.setText(note.tanggal)
                    etContent.setText(note.isi)
                } else {
                    Toast.makeText(this@EditNoteActivity_126, "Catatan tidak ditemukan atau akses ditolak", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
