package com.example.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db:NoteDatebaseHelper
    private lateinit var  notesAdopter:NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= NoteDatebaseHelper(this)
        notesAdopter=NotesAdapter(db.getallnote(), this)
        binding.noterecycleview.layoutManager=LinearLayoutManager(this)
        binding.noterecycleview.adapter=notesAdopter
        binding.addBTN.setOnClickListener{
            intent= Intent(this,AddNoteactivity::class.java)
            startActivity(intent)
        }}
    // Inside onResume() method

    override fun onResume() {
        super.onResume()
        notesAdopter =NotesAdapter(db.getallnote(), this)
        binding.noterecycleview.adapter = notesAdopter
    }

}