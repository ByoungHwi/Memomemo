package com.ybh.memomemo.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ybh.memomemo.view.main.MainActivity
import com.ybh.memomemo.view.home.adapter.NoteListAdapter
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.R
import com.ybh.memomemo.view.home.presenter.HomeContract
import com.ybh.memomemo.view.home.presenter.HomePresenter
import com.ybh.memomemo.view.note.addNote.AddNoteFragment
import com.ybh.memomemo.view.note.showNote.ShowNoteFragment

class HomeFragment : Fragment() , HomeContract.View
{

    private lateinit var noteListAdapter : NoteListAdapter
    private lateinit var presenter : HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home,null)
        val rv_notedata = view.findViewById<RecyclerView>(R.id.rv_notedata_list)

        presenter = HomePresenter()
        presenter.takeView(this)


        noteListAdapter = NoteListAdapter(
            UserData.userNoteList!!,
            view.context
        ) { noteData -> presenter.itemClicked(noteData) }

        rv_notedata.layoutManager = LinearLayoutManager(view.context)
        rv_notedata.adapter = noteListAdapter

        val itemTouchHelper : ItemTouchHelper = ItemTouchHelper(SwipeCallback(0,ItemTouchHelper.LEFT))
        itemTouchHelper.attachToRecyclerView(rv_notedata)

        val imgbtn_add = view.findViewById<ImageButton>(R.id.imgbtn_add)
        imgbtn_add.setOnClickListener(){ presenter.addBottonClicked() }

        return view
    }

    inner class SwipeCallback(dragDirs : Int, swipeDirs : Int ) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs)
    {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            presenter.onSwiped(noteListAdapter,viewHolder.layoutPosition)
        }
    }

    override fun refreshRecyclerView() {
        noteListAdapter.notifyDataSetChanged()
    }

    override fun startAddNoteFragment() {
        (activity as MainActivity).replaceFragment(AddNoteFragment.newInstance())
    }

    override fun startShowNoteFragment() {
        (activity as MainActivity).replaceFragment(ShowNoteFragment.newInstance())
    }
}