package com.ybh.memomemo.view.note.showNote.presenter

import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.view.note.baseNote.presenter.NotePresenter

class ShowNotePresenter : ShowNoteContract.Presenter , NotePresenter(){

    override fun getNoteData() {
        if(isTitleEmpty())
        {
            view.hideTitle()

        }else
        {
            view.setTitle(UserData.currentNote.title!!)
        }
        view.setBody(UserData.currentNote.text?:"")
    }

    fun isTitleEmpty() : Boolean{
        return(UserData.currentNote.title==null|| UserData.currentNote.title=="")
    }
}