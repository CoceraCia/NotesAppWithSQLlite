package com.coceracia.sqlnotes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coceracia.sqlnotes.R
import com.coceracia.sqlnotes.model.Note

class NoteAdapter(private val listNotes: List<Note>): RecyclerView.Adapter<NoteAdapter.noteViewHolder>() {
    class noteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvNoteRecyclerTitle)
        val date = itemView.findViewById<TextView>(R.id.tvNoteRecyclerDate)
        val description = itemView.findViewById<TextView>(R.id.tvNoteRecyclerDescr)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteAdapter.noteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_recycler, parent, false)
        return noteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.noteViewHolder, position: Int) {
        val note = listNotes[position]
        holder.title.text = note.title.uppercase()
        holder.date.text = note.date
        val description = note.content.substring(0,60) + "..."
        holder.description.text = description
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
}