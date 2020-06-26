package com.ybh.memomemo.view.note.baseNote.presenter

open class NotePresenter() :
    NoteContract.Presenter<NoteContract.View> {

    lateinit var view : NoteContract.View
    override fun takeView(view: NoteContract.View) {
        this.view = view
    }
}