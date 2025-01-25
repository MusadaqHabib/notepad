package com.example.notepad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(private var notes: List<note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db:NoteDatebaseHelper = NoteDatebaseHelper(context)

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textviewtitle)
        val contentTextView: TextView = itemView.findViewById(R.id.textcontentview)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateBTN)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]

        holder.contentTextView.text = currentNote.content
        holder.titleTextView.text = currentNote.title

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(currentNote.date)
        holder.dateTextView.text = "Last Updated: $formattedDate"

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, updateNoteActivity::class.java).apply {
                putExtra("note_id", currentNote.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deletenote(currentNote.id)
            refreshData(db.getallnote())
            Toast.makeText(holder.itemView.context, "Deleted note", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes: List<note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
