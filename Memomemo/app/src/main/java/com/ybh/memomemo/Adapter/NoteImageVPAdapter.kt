package com.ybh.memomemo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ybh.memomemo.Activity.FRAGMENT_ADD
import com.ybh.memomemo.Activity.FRAGMENT_EDIT
import com.ybh.memomemo.Activity.FRAGMENT_SHOW
import com.ybh.memomemo.Data.NoteImage
import com.ybh.memomemo.Others.GlideApp
import com.ybh.memomemo.R

class NoteImageVPAdapter(val context: Context, val items : MutableList<NoteImage>, val attachedFragment : Short ) : PagerAdapter()
{
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_image_viewpager,container,false)

        val itemImage : ImageView = view.findViewById(R.id.image_item_viewpager)
        GlideApp.with(context).load(items[position].uri).into(itemImage)
        val deleteButton : ImageButton = view.findViewById(R.id.imgbtn_delete_viewpager)
        when(attachedFragment)
        {
            FRAGMENT_SHOW -> deleteButton.visibility=View.GONE
            FRAGMENT_ADD, FRAGMENT_EDIT ->{
                deleteButton.setOnClickListener(){
                    items.removeAt(position)
                    //UserData.currentImages.removeAt(position)
                    notifyDataSetChanged()
                }
            }
        }
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}