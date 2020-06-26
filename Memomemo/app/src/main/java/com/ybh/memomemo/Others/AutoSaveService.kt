package com.ybh.memomemo.Others

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.ybh.memomemo.view.main.FRAGMENT_ADD
import com.ybh.memomemo.view.main.MainActivity

class AutoSaveService(val context : Context) : Service(){
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {

        if((context as MainActivity).getAttachedFragment() == FRAGMENT_ADD)
        {
            (context as MainActivity).saveNote()
        }
        stopSelf()
    }
}