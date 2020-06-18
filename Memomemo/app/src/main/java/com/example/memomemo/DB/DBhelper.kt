package com.example.memomemo.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.memomemo.Data.NoteData
import com.example.memomemo.Data.NoteImage


class DBhelper(context : Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VIRSION )
{
    companion object{
        private val DB_NAME = "S_NOTE_DB"
        private val DB_VIRSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Notes(id Integer PRIMARY KEY AUTOINCREMENT, title Text, text Text, date Long, thumbnail Text)")
        db?.execSQL("CREATE TABLE Images(id Integer PRIMARY KEY AUTOINCREMENT, nid Integer, uri Text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addNote(noteData:NoteData) : Int {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("title",noteData.title)
        values.put("text",noteData.text)
        values.put("date",System.currentTimeMillis())
        values.put("thumbnail",noteData.thumnail)
        val id = db.insert("Notes",null,values)
        db.close()

        return id.toInt()
    }

    fun updateNote(noteData: NoteData) {
        val db = this.writableDatabase
        db.execSQL("UPDATE Notes SET title = '${noteData.title}', text = '${noteData.text}' WHERE id = '${noteData.id}'")
        db.close()
    }

    fun deleteNote(id : Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Notes WHERE id ='${id}';")
        db.close()
    }

    fun getNote(id : Long) : NoteData {
        val db = this.readableDatabase
        val cusor = db.rawQuery("SELECT * FROM Notes WHERE id ='${id}'",null)
        var noteData : NoteData? = null

        if(cusor.moveToFirst()) {
            noteData = NoteData(cusor.getInt(0),cusor.getString(1),cusor.getString(2),cusor.getLong(3),
                cusor.getString(4))
        }
        db.close()

        return noteData!!
    }

    fun getNoteList() : MutableList<NoteData> {
        val noteList = mutableListOf<NoteData>()
        val db = this.readableDatabase
        val cusor = db.rawQuery("SELECT * FROM Notes",null)

        while(cusor.moveToNext())
        {
            noteList.add(NoteData(cusor.getInt(0),cusor.getString(1),cusor.getString(2),cusor.getLong(3),cusor.getString(4)))
        }
        db.close()

        noteList.reverse()

        return noteList
    }

    fun addImages(images : MutableList<NoteImage>) {
        val db = this.writableDatabase

        for(image : NoteImage in images)
        {
            val values = ContentValues()
            values.put("nid",image.nid)
            values.put("uri",image.uri)
            db.insert("Images",null,values)
        }
        db.close()
    }

    fun updateImages(images : MutableList<NoteImage>, nid: Int) {
        val db = this.writableDatabase

        for(image : NoteImage in images)
        {
            if(image.nid == null)
            {
                val values = ContentValues()
                values.put("nid",nid)
                values.put("uri",image.uri)
                db.insert("Images",null,values)
            }
        }
        db.close()
    }

    fun findImagesByNid(nid : Int) : MutableList<NoteImage> {
        val returnImages : MutableList<NoteImage> = mutableListOf()
        val db = this.readableDatabase
        val cusor = db.rawQuery("SELECT * FROM Images WHERE nid = '${nid}'",null)

        while(cusor.moveToNext())
        {
            returnImages.add(NoteImage(cusor.getInt(0),cusor.getInt(1),cusor.getString(2)))
        }
        db.close()

        return returnImages
    }

    fun deleteImageById(id : Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Images WHERE id ='${id}';")
        db.close()
    }

    fun deleteImagesByNid(nid : Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Images WHERE nid ='${nid}';")
        db.close()
    }
}