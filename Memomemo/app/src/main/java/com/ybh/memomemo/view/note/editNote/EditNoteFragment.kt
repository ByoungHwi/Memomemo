package com.ybh.memomemo.view.note.editNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.R
import com.ybh.memomemo.view.note.baseNote.WritableNoteFragment
import com.ybh.memomemo.view.note.editNote.presenter.EditNoteContract
import com.ybh.memomemo.view.note.editNote.presenter.EditNotePresenter

class EditNoteFragment : WritableNoteFragment(), EditNoteContract.View
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        (presenter as EditNotePresenter).getNote()

        return view
    }

    override fun setPresenter() {
        presenter = EditNotePresenter()
        (presenter as EditNotePresenter).takeView(this)
    }

    override fun setFocus()
    {
        bodyEditText.requestFocus()
    }

    companion object{
        fun newInstance() : EditNoteFragment { return EditNoteFragment()
        }
    }



}