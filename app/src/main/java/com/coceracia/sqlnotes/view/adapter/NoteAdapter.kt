package com.coceracia.sqlnotes.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coceracia.sqlnotes.R
import com.coceracia.sqlnotes.model.Note

class NoteAdapter(
    private val listNotes: List<Note>,
    private val onClick: (Note) -> Unit,
    private val onLongClick: (Note) -> Unit
): RecyclerView.Adapter<NoteAdapter.noteViewHolder>() {
    class noteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvNoteRecyclerTitle)
        val date = itemView.findViewById<TextView>(R.id.tvNoteRecyclerDate)
        val description = itemView.findViewById<TextView>(R.id.tvNoteRecyclerDescr)
        val sep = itemView.findViewById<View>(R.id.vSepNotes)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteAdapter.noteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_recycler, parent, false)
        return noteViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteAdapter.noteViewHolder, position: Int) {
        val note = listNotes[position]
        if (note.title.trim() == "") {
            holder.title.text = "NEW NOTE"
        } else {
            holder.title.text = note.title.uppercase()
        }

        holder.date.text = note.date
        if (note.content.trim() == "") {
            holder.description.text = "Your text"
        } else if (note.content.trim().lines().size >= 2) {
            val notes = note.content.trim().lines().take(2)
            holder.description.text = notes[0] + notes[1] + "..."
        } else if(note.content.length > 60){
            holder.description.text = note.content.substring(0, 60) + "..."
        } else {
            holder.description.text = note.content
        }

        if (position == listNotes.size - 1) {
            holder.sep.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.itemView.setOnClickListener {
            onClick(note)
        }
        holder.itemView.setOnLongClickListener{
            onLongClick(note)
            true
        }
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
}