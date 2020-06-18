package com.ybh.memomemo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AddNoteFragment : WritableNoteFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        return view
    }

    companion object{
        fun newInstance() : AddNoteFragment{ return AddNoteFragment() }
    }
}