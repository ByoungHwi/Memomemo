package com.ybh.memomemo.view.home.presenter

import com.ybh.memomemo.Data.NoteData
import com.ybh.memomemo.view.home.adapter.NoteListAdapter

interface HomeContract {
    interface View{
        fun startShowNoteFragment()
        fun startAddNoteFragment()
        fun refreshRecyclerView()
    }
    interface Presenter{
        fun takeView(view: View)
        fun itemClicked(noteData: NoteData)
        fun addBottonClicked()
        fun onSwiped(noteListAdapter: NoteListAdapter, position: Int)

    }
}