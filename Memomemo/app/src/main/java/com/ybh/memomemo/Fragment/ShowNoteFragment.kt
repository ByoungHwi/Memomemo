package com.ybh.memomemo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.R

class ShowNoteFragment() : NoteFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        if(UserData.currentNote.title==null||UserData.currentNote.title=="")
        {
            view?.findViewById<EditText>(R.id.et_title_note)?.visibility = View.GONE
            view?.findViewById<TextView>(R.id.tv_title_note)?.visibility = View.GONE
        }else
        {
            view?.findViewById<EditText>(R.id.et_title_note)?.setText(UserData.currentNote.title)
            view?.findViewById<EditText>(R.id.et_title_note)?.isFocusable=false
        }

        view?.findViewById<EditText>(R.id.et_body_note)?.setText(UserData.currentNote.text)
        view?.findViewById<EditText>(R.id.et_body_note)?.isFocusable=false

        return view
    }

    companion object{
        fun newInstance() : ShowNoteFragment{ return ShowNoteFragment() }
    }



}