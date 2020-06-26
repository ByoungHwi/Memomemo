package com.ybh.memomemo.view.note.editNote.presenter

interface EditNoteContract {
    interface View{
        fun setFocus()
    }

    interface Presenter {
        fun getNote()
    }

}