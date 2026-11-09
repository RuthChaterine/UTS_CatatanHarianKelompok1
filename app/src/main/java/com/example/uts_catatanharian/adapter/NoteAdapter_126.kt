package com.example.uts_catatanharian.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.data.entity.NoteEntity_126

class NoteAdapter_126(
    private var noteList: List<NoteEntity_126>,
    private val onEditClick: (NoteEntity_126) -> Unit,
    private val onDeleteClick: (NoteEntity_126) -> Unit
) : RecyclerView.Adapter<NoteAdapter_126.NoteViewHolder_126>() {

    class NoteViewHolder_126(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_126)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_126)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content_snippet_126)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btn_edit_126)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete_126)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder_126 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_126, parent, false)
        return NoteViewHolder_126(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder_126, position: Int) {
        val note = noteList[position]
        holder.tvTitle.text = note.judul
        holder.tvDate.text = note.tanggal
        holder.tvContent.text = note.isi

        holder.btnEdit.setOnClickListener {
            onEditClick(note)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(note)
        }
    }

    override fun getItemCount(): Int = noteList.size

    fun updateNotes_126(newNotes: List<NoteEntity_126>) {
        this.noteList = newNotes
        notifyDataSetChanged()
    }
}
