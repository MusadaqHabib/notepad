package com.example.notepad
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.Toast
import com.example.notepad.databinding.ActivityMainBinding
import com.example.notepad.databinding.ActivityUpdateBinding

class updateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:NoteDatebaseHelper
    private var Noteid:Int= -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= NoteDatebaseHelper(this)

        Noteid=intent.getIntExtra("note_id",-1)
        if(Noteid==-1){
            finish()
            return
        }
        val note= db.getNoteByID(Noteid)


        binding.updatetitleditText.setText(note.title)
        binding.updatecontenteditext.setText(note.content)
        binding.editsavebutton.setOnClickListener {
            val newTilte=binding.updatetitleditText.text.toString()
            val newContent=binding.updatecontenteditext.text.toString()
            val updatedNote=note(Noteid,newTilte,newContent)
            db.update(updatedNote)
            finish()
            Toast.makeText(this,"save changes",Toast.LENGTH_SHORT).show()
        }


    }
}