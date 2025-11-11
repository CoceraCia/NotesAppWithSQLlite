package com.coceracia.sqlnotes.view

import android.R.attr.onClick
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coceracia.sqlnotes.R
import com.coceracia.sqlnotes.model.Note
import com.coceracia.sqlnotes.view.adapter.NoteAdapter
import com.google.android.material.button.MaterialButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import kotlin.collections.listOf

class MainActivity : AppCompatActivity() {
    private var listNotes = mutableListOf<Note>()

    private val noteEditorLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { r ->
        if (r.resultCode == Activity.RESULT_OK) {
            val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                r.data?.getParcelableExtra("NOTE", Note::class.java)
            } else {
                @Suppress("DEPRECATION")
                r.data?.getParcelableExtra<Note>("NOTE")
            }
            Log.d("NoteNotExists", "loaded")

            if (note != null){
                if (!noteExists(note)){
                    Log.d("Not Exists", note.toString())
                    listNotes.add(note)
                } else {
                    Log.d("Exists", note.toString())
                    updateValues(note)
                }
            } else {
                Log.d("Null", note.toString())
            }
            showNotes()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showDay()
        showNotes()


        val bttnAdd = findViewById<MaterialButton>(R.id.ibAdd)
        bttnAdd.setOnClickListener {
            val intentNoteEditor = Intent(this, NoteEditorActivity::class.java)
            noteEditorLauncher.launch(intentNoteEditor)
        }
    }

    fun showNotes() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvNotesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoteAdapter(listNotes, onClick = { n ->
            val intent = Intent(this, NoteEditorActivity::class.java)
            intent.putExtra("NOTE", n)
            noteEditorLauncher.launch(intent)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDay() {
        val dateTxt = findViewById<TextView>(R.id.tvTitle)
        dateTxt.text = LocalDateTime.now().dayOfWeek.toString()
    }

    fun noteExists(note: Note): Boolean {
        return !listNotes.all { it.id != note.id}
    }

    fun updateValues(note:Note){
        listNotes.forEach {
            if (it.id == note.id){
                it.title = note.title
                it.date = note.date
                it.content = note.content
            }
        }
    }
}