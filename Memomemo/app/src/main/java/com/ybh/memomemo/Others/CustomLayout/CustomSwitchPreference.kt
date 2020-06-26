package com.ybh.memomemo.Others.CustomLayout

import android.widget.Switch
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreference
import com.ybh.memomemo.R


class CustomSwitchPreference : SwitchPreference {

    @SuppressLint("NewApi")
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}


    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val theSwitch = holder?.itemView?.findViewById<Switch>(android.R.id.switch_widget)
        theSwitch?.setThumbResource(R.drawable.switch_thumb_selector)
        theSwitch?.setTrackResource(R.drawable.switch_track_selector)
    }


}