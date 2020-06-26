package com.ybh.memomemo.view.password.passwordSetting.presenter

import com.ybh.memomemo.view.password.presenter.PasswordContract

interface PasswordSettingContract {
    interface View : PasswordContract.View{
        fun savePassword(password : String)
        fun finishActivity()
    }

    interface Presenter {

    }
}