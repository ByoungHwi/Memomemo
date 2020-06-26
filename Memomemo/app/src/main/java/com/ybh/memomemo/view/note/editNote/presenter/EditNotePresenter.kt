package com.ybh.memomemo.view.note.editNote.presenter

import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.view.note.baseNote.presenter.WritableNotePresenter

class EditNotePresenter : WritableNotePresenter(), EditNoteContract.Presenter {

    override fun getNote() {
        view.setTitle(UserData.currentNote.title?:"")
        view.setBody(UserData.currentNote.text?:"")
        (view as EditNoteContract.View).setFocus()

        UserData.beforeEdit()
    }
}