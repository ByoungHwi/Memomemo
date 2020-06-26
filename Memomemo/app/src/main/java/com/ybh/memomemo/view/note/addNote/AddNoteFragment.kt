package com.ybh.memomemo.view.note.addNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ybh.memomemo.view.note.baseNote.WritableNoteFragment

class AddNoteFragment : WritableNoteFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        return view
    }

    companion object{
        fun newInstance() : AddNoteFragment { return AddNoteFragment()
        }
    }
}