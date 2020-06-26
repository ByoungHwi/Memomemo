package com.ybh.memomemo.view.note.showNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ybh.memomemo.view.note.baseNote.NoteFragment
import com.ybh.memomemo.view.note.showNote.presenter.ShowNoteContract
import com.ybh.memomemo.view.note.showNote.presenter.ShowNotePresenter

class ShowNoteFragment() : NoteFragment(), ShowNoteContract.View
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        titleEditText.isFocusable = false
        bodyEditText.isFocusable = false

        (presenter as ShowNotePresenter).getNoteData()

        return view
    }

    override fun setPresenter() {
        presenter = ShowNotePresenter()
        (presenter as ShowNotePresenter).takeView(this)
    }

    companion object{
        fun newInstance() : ShowNoteFragment { return ShowNoteFragment()
        }
    }
}