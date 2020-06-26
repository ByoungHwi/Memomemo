package com.ybh.memomemo.view.home.presenter

import com.ybh.memomemo.Data.NoteData
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.view.home.HomeFragment
import com.ybh.memomemo.view.home.adapter.NoteListAdapter
import com.ybh.memomemo.view.main.MainActivity

class HomePresenter : HomeContract.Presenter
{
    lateinit var view : HomeContract.View

    override fun takeView(view: HomeContract.View) {
        this.view = view
    }

    override fun addBottonClicked() {
       view.startAddNoteFragment()
    }

    override fun itemClicked(noteData: NoteData) {
        UserData.currentNote = noteData
        ((view as HomeFragment).activity as MainActivity).setCurrentImages()
        view.startShowNoteFragment()
    }

    override fun onSwiped(noteListAdapter: NoteListAdapter,position: Int) {
        UserData.currentNote = UserData.userNoteList?.get(position)!!
        noteListAdapter.items.remove(UserData.currentNote)
        noteListAdapter.notifyDataSetChanged()
        ((view as HomeFragment).activity as MainActivity).deleteNote()
    }

}