package com.ybh.memomemo.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager

class PasswordCheckActivity : PasswordActivity()
{
    lateinit var prefs : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs = getSharedPreferences("MAIN_SHARED_PREF",0)
        if(!defaultPrefs.getBoolean("isLock",false) || prefs.getString("PASSWORD","NO_PASSWORD") == "NO_PASSWORD")
        {
            val intent : Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }



        super.setExplanationText("비밀번호를 입력해 주세요")

    }

    override fun onPasswordInputFinish() {

        prefs = getSharedPreferences("MAIN_SHARED_PREF",0)
        if(prefs.getString("PASSWORD","NO_PASSWORD") == super.getCurrentInput())
        {
            val intent : Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }else
        {
            super.setExplanationText("비밀번호가 일치하지 않습니다.\n다시 입력해 주세요")
            super.refreshInput()
        }

    }
}