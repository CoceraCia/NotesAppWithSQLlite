package com.coceracia.sqlnotes.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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
            val title = r.data?.getStringExtra("TITLE")
            val content = r.data?.getStringExtra("CONTENT")
            val date = r.data?.getStringExtra("DATE")
            Log.d("TITLE", title.toString())
            Log.d("CONTENT", content.toString())
            listNotes.add(
                Note(
                    title.toString(),
                    date.toString(),
                    title.toString()
                )
            )
            showNotes()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showNotes()



        val bttnAdd = findViewById<MaterialButton>(R.id.ibAdd)
        bttnAdd.setOnClickListener {
            val intentNoteEditor = Intent(this, NoteEditorActivity::class.java)
            noteEditorLauncher.launch(intentNoteEditor)
        }
    }

    fun showNotes(){
        val recyclerView = findViewById<RecyclerView>(R.id.rvNotesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoteAdapter(listNotes)
    }
}