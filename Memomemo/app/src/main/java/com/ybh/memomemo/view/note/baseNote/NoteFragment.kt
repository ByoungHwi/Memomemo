package com.ybh.memomemo.view.note.baseNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ybh.memomemo.view.main.MainActivity
import com.ybh.memomemo.view.note.adpater.NoteImageVPAdapter
import com.ybh.memomemo.Others.CustomLayout.MeasueredViewPager
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.R
import com.google.android.material.tabs.TabLayout
import com.ybh.memomemo.view.note.baseNote.presenter.NoteContract
import com.ybh.memomemo.view.note.baseNote.presenter.NotePresenter

open class NoteFragment : Fragment(), NoteContract.View
{
    open lateinit var presenter : Any

    lateinit var titleTextView: TextView
    lateinit var titleEditText: EditText
    lateinit var bodyEditText: EditText
    lateinit var imageViewPager: MeasueredViewPager
    lateinit var viewPagerAdapter: NoteImageVPAdapter
    lateinit var tabLayout : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_note,null)

        setPresenter()

        titleTextView = view?.findViewById<TextView>(R.id.tv_title_note)!!
        titleEditText = view.findViewById<EditText>(R.id.et_title_note)
        bodyEditText = view.findViewById<EditText>(R.id.et_body_note)
        imageViewPager = view.findViewById(R.id.vp_img_note)
        viewPagerAdapter = NoteImageVPAdapter(
            view.context,
            UserData.currentImages,
            (activity as MainActivity).getAttachedFragment()
        )
        imageViewPager.adapter = viewPagerAdapter
        tabLayout = view.findViewById(R.id.tl_img_note)
        tabLayout.setupWithViewPager(imageViewPager)

        return view
    }

    open fun setPresenter() {
        presenter = NotePresenter()
        (presenter as NotePresenter).takeView(this)
    }

    override fun setTitle(title: String) {
        titleEditText.setText(title)
    }

    override fun setBody(body: String) {
        bodyEditText.setText(body)
    }

    override fun hideTitle() {
        titleTextView.visibility = View.GONE
        titleEditText.visibility = View.GONE
    }

}

