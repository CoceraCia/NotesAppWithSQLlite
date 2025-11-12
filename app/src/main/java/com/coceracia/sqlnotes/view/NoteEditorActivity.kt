package com.coceracia.sqlnotes.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coceracia.sqlnotes.R
import com.coceracia.sqlnotes.model.Note
import com.google.android.material.button.MaterialButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteEditorActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_editor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("NOTE", Note::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Note>("NOTE")
        }

        setValues(note)
        val btnCheck = findViewById<MaterialButton>(R.id.mbCheckButton)
        btnCheck.setOnClickListener {
            Log.d("button", "t")
            addNote(note)
        }
    }

    fun setValues(n: Note?) {
        val title = findViewById<EditText>(R.id.etTitleNote)
        val content = findViewById<EditText>(R.id.etContentNote)
        if (n != null) {
            title.setText(n.title)
            content.setText(n.content)
        } else {
            title.setText("")
            content.setText("")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNote(n: Note?) {
        val intent = Intent()
        val title = findViewById<EditText>(R.id.etTitleNote)
        val content = findViewById<EditText>(R.id.etContentNote)
        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .toString()
        if (n != null) {
            n.title = title.text.toString()
            n.content = content.text.toString()
            n.date = date
            n.lastModified = System.currentTimeMillis()
            intent.putExtra("NOTE", n)
            Log.d("NoteExists", n.toString())
        } else {
            val note = Note(title.text.toString(), date, content.text.toString())
            intent.putExtra("NOTE", note)
            Log.d("NoteNotExists", note.toString())
        }
        Log.d("search", "")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}