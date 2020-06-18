package com.example.memomemo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.memomemo.Activity.MainActivity
import com.example.memomemo.Adapter.NoteImageVPAdapter
import com.example.memomemo.CustomLayout.MeasueredViewPager
import com.example.memomemo.Data.UserData
import com.example.memomemo.R
import com.google.android.material.tabs.TabLayout

open class NoteFragment : Fragment()
{
    lateinit var imageViewPager: MeasueredViewPager
    lateinit var viewPagerAdapter: NoteImageVPAdapter
    lateinit var tabLayout : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_note,null)

        imageViewPager = view.findViewById(R.id.vp_img_note)
        viewPagerAdapter = NoteImageVPAdapter(view.context,UserData.currentImages,(activity as MainActivity).getAttachedFragment())
        imageViewPager.adapter = viewPagerAdapter
        tabLayout = view.findViewById(R.id.tl_img_note)
        tabLayout.setupWithViewPager(imageViewPager)

        return view
    }


}