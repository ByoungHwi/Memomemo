package com.ybh.memomemo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.R

class EditNoteFragment : WritableNoteFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        view?.findViewById<EditText>(R.id.et_title_note)?.setText(UserData.currentNote.title)
        view?.findViewById<EditText>(R.id.et_body_note)?.setText(UserData.currentNote.text)
        view?.findViewById<EditText>(R.id.et_body_note)?.requestFocus()

        UserData.beforeEdit()

        return view
    }

    companion object{
        fun newInstance() : EditNoteFragment{ return EditNoteFragment() }
    }



}