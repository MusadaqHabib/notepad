package com.example.notepad

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
class NoteDatebaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_Version) {
    companion object{
        private const val DATABASE_NAME ="notessapp.db"
        private const val DATABASE_Version =1
        private const val TABLE_NAME="allnotes"
        private const val COULMN_ID="id"
        private const val COULMN_TITLE="title"
        private const val COULMN_CONTENT="content"
        private const val COULMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val creatqury = "CREATE TABLE $TABLE_NAME($COULMN_ID INTEGER PRIMARY KEY,$COULMN_TITLE TEXT,$COULMN_CONTENT TEXT,$COULMN_DATE INTEGER)"
        db?.execSQL(creatqury)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertnote(note: note)
    {
        val db=writableDatabase
        val value= ContentValues().apply{
            put(COULMN_TITLE,note.title)
            put(COULMN_CONTENT,note.content)
            put(COULMN_DATE, note.date.time)

        }
        db.insert(TABLE_NAME,null,value)
        db.close()
    }
    fun getallnote():List<note> {
        val noteslist = mutableListOf<note>()
        val db = readableDatabase
        val qury = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(qury, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COULMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COULMN_TITLE))
            val cotent = cursor.getString(cursor.getColumnIndexOrThrow(COULMN_CONTENT))
            val dateMillis = cursor.getLong(cursor.getColumnIndexOrThrow(COULMN_DATE))
            val date = Date(dateMillis)
            var Note=note(id,title,cotent,date)
            noteslist.add(Note)
        }
        cursor.close()
        db.close()
        return noteslist
    }
    fun update(note: note){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(COULMN_TITLE,note.title)
            put(COULMN_CONTENT,note.content)
        }
        val whereClause="$COULMN_ID = ?"
        val whereArgs= arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    fun getNoteByID(noteId: Int): note {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COULMN_ID=$noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COULMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COULMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COULMN_CONTENT))

        cursor.close()
        db.close()
        return note(id, title, content)
    }
    fun deletenote(Noteid: Int){
        val db=writableDatabase
        val whereClause="$COULMN_ID = ?"
        val whereArgs= arrayOf(Noteid.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()

    }




}

