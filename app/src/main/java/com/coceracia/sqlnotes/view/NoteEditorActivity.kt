package com.coceracia.sqlnotes.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
        val btnCheck = findViewById<MaterialButton>(R.id.mbCheckButton)
        btnCheck.setOnClickListener {
            val intent = Intent()
            val title = findViewById<EditText>(R.id.etTitleNote)
            val content = findViewById<EditText>(R.id.etContentNote)
            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .toString()
            intent.putExtra("TITLE", title.text.toString())
            intent.putExtra("CONTENT", content.text.toString())
            intent.putExtra("DATE", date)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}