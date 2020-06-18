package com.example.memomemo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memomemo.Activity.MainActivity
import com.example.memomemo.Adapter.NoteListAdapter
import com.example.memomemo.Data.UserData
import com.example.memomemo.R

class HomeFragment : Fragment()
{

    private lateinit var noteListAdapter : NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home,null)
        val rv_notedata = view.findViewById<RecyclerView>(R.id.rv_notedata_list)

        noteListAdapter = NoteListAdapter(UserData.userNoteList!!,view.context){ noteData ->
            UserData.currentNote = noteData
            (activity as MainActivity).setCurrentImages()
            (activity as MainActivity).replaceFragment(ShowNoteFragment.newInstance()) }

        rv_notedata.layoutManager = LinearLayoutManager(view.context)
        rv_notedata.adapter = noteListAdapter

        val itemTouchHelper : ItemTouchHelper = ItemTouchHelper(SwipeCallback(0,ItemTouchHelper.LEFT))
        itemTouchHelper.attachToRecyclerView(rv_notedata)

        val imgbtn_add = view.findViewById<ImageButton>(R.id.imgbtn_add)
        imgbtn_add.setOnClickListener(){ (activity as MainActivity).replaceFragment(AddNoteFragment.newInstance()) }

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
            UserData.currentNote = UserData.userNoteList?.get(viewHolder.layoutPosition)!!
            noteListAdapter.items.remove(UserData.currentNote)
            noteListAdapter.notifyDataSetChanged()
            (activity as MainActivity).deleteNote()
        }
    }
}