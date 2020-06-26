package com.ybh.memomemo.view.password.passwordSetting

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import com.ybh.memomemo.view.password.PasswordActivity
import com.ybh.memomemo.view.password.passwordSetting.presenter.PasswordSettingContract
import com.ybh.memomemo.view.password.passwordSetting.presenter.PasswordSettingPresenter

class PasswordSettingActivity : PasswordActivity() , PasswordSettingContract.View
{
    //var isFirstInput : Boolean = true
    //lateinit var firstInput : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setExplanationText("비밀번호를 입력해 주세요")


    }

    override fun finishActivity(){
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun savePassword(password : String){
        val prefs : SharedPreferences = this.getSharedPreferences("MAIN_SHARED_PREF",0)
        prefs.edit().putString("PASSWORD",password).apply()
    }

    override fun setPresenter() {
        presenter = PasswordSettingPresenter()
        (presenter as PasswordSettingPresenter).takeView(this)
    }
/*
    override fun onPasswordInputFinish() {
        when(isFirstInput)
        {
            true -> {
                firstInput = super.getCurrentInput()
                super.refreshInput()
                super.setExplanationText("다시 한번 입력해 주세요")
                isFirstInput = false
            }
            false -> {
                if(firstInput == super.getCurrentInput())
                {
                    val prefs : SharedPreferences = this.getSharedPreferences("MAIN_SHARED_PREF",0)
                    prefs.edit().putString("PASSWORD",firstInput).apply()

                    //비밀번호 일치
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                else
                {
                    super.refreshInput()
                    super.setExplanationText("비밀번호가 일치하지 않습니다.\n다시 입력해 주세요")
                    isFirstInput = true
                }
            }
        }
    }

 */
}