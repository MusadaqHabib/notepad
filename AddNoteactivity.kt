package com.example.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.util.*
import com.example.notepad.databinding.ActivityAddNoteactivityBinding

class AddNoteactivity : AppCompatActivity() {
    private lateinit var  binding:ActivityAddNoteactivityBinding
    private lateinit var db:NoteDatebaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db=NoteDatebaseHelper(this)
        binding.savebutton.setOnClickListener{
            var title=binding.titleditText.text.toString()
            var content=binding.contenteditext.text.toString()
            val id = 0
            val currentDate = Date()
            var Note=note(id,title,content, currentDate)
            db.insertnote(Note)
            finish()
            Toast.makeText(this,"note save secessfully ",Toast.LENGTH_SHORT).show()
        }
    }
}