package com.coceracia.sqlnotes.view

import android.R.attr.onClick
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coceracia.sqlnotes.R
import com.coceracia.sqlnotes.model.Note
import com.coceracia.sqlnotes.view.adapter.NoteAdapter
import com.coceracia.sqlnotes.viewmodel.MainActivityViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private val mViewModel : MainActivityViewModel by viewModels()
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

            if (note != null){
                lifecycleScope.launch {
                    if (!mViewModel.noteExists(note.id)){
                        mViewModel.insert(note)
                    } else {
                        mViewModel.update(note)
                    }
                }
            }

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


        val bttnAdd = findViewById<MaterialButton>(R.id.ibAdd)
        bttnAdd.setOnClickListener {
            val intentNoteEditor = Intent(this, NoteEditorActivity::class.java)
            noteEditorLauncher.launch(intentNoteEditor)
        }

        mViewModel.allNotes.observe(this) {notes ->
            notes.forEach {
                Log.d("Note", it.toString())
            }
            showContent(notes)
        }
    }

    fun showContent(list : List<Note>) {
        val recyclerView = findViewById<RecyclerView>(R.id.rvNotesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoteAdapter(list, onClick = { n ->
            val intent = Intent(this, NoteEditorActivity::class.java)
            intent.putExtra("NOTE", n)
            noteEditorLauncher.launch(intent)
        }, onLongClick = {n->
            showDeleteDialog(n)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDay() {
        val dateTxt = findViewById<TextView>(R.id.tvTitle)
        dateTxt.text = LocalDateTime.now().dayOfWeek.toString()
    }

    fun showDeleteDialog(note:Note){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val alertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        val bttnDelete = dialogView.findViewById<MaterialButton>(R.id.mbButtonDelete)
        bttnDelete.setOnClickListener {
            mViewModel.delete(note)
            alertDialog.dismiss()
        }
    }
}