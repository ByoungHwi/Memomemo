package com.ybh.memomemo.view.note.baseNote.presenter

interface NoteContract{

    interface View{
        fun setTitle(title : String)
        fun setBody(body : String)
        fun hideTitle()
    }

    interface Presenter<T>{
        fun takeView(view : T)
    }

}