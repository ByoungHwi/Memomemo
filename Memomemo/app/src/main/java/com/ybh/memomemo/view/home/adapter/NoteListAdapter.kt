package com.ybh.memomemo.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ybh.memomemo.Data.NoteData
import com.ybh.memomemo.Others.GlideApp
import com.ybh.memomemo.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_notedata.*
import java.text.SimpleDateFormat

class NoteListAdapter( val items: MutableList<NoteData>, val context: Context, val itemSelect: (NoteData) -> Unit) :RecyclerView.Adapter<NoteListAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position],context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_notedata, parent, false)
        return ViewHolder(view,itemSelect)
    }

    inner class ViewHolder( override val containerView: View, itemSelect : (NoteData) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer
    {
        fun bind(noteData: NoteData, context: Context)
        {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            tv_date_item_notedata.text = sdf.format(noteData.date)
            if(noteData.title=="" || noteData.title ==null)
            {
                tv_title_item_notedata.text = "제목없음"
            }else
            {
                tv_title_item_notedata.text = noteData.title
            }
            tv_text_item_notedata.text = noteData.text

            if(noteData.thumnail == null) {
                ll_dataimage_item_notedata.visibility = View.GONE
            }
            else {
                GlideApp.with(context).load(noteData.thumnail).circleCrop().thumbnail(0.1f).into(img_dataimage_item_notedata)
            }
            itemView.setOnClickListener() {itemSelect(noteData)}
        }
    }

}

