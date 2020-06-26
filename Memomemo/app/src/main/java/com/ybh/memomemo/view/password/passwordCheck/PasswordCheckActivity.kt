package com.ybh.memomemo.view.password.passwordCheck

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.ybh.memomemo.view.main.MainActivity
import com.ybh.memomemo.view.password.PasswordActivity
import com.ybh.memomemo.view.password.passwordCheck.presenter.PassWordCheckContract
import com.ybh.memomemo.view.password.passwordCheck.presenter.PassWordCheckPresenter

class PasswordCheckActivity : PasswordActivity(), PassWordCheckContract.View
{
    lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs = getSharedPreferences("MAIN_SHARED_PREF",0)
        if(!defaultPrefs.getBoolean("isLock",false) || prefs.getString("PASSWORD","NO_PASSWORD") == "NO_PASSWORD")
        {
            startMainActiviy()
        }

        super.setExplanationText("비밀번호를 입력해 주세요")

    }

    override fun startMainActiviy() {
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun setPresenter() {
        presenter = PassWordCheckPresenter()
        (presenter as PassWordCheckPresenter).takeView(this)
    }

}