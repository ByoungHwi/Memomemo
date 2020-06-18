package com.example.memomemo.Others

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.memomemo.Activity.FRAGMENT_ADD
import com.example.memomemo.Activity.MainActivity

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