package com.ybh.memomemo.view.password.passwordCheck.presenter

import com.ybh.memomemo.view.password.presenter.PasswordContract

interface PassWordCheckContract {
    interface View : PasswordContract.View{
        fun startMainActiviy()
    }

    interface Presenter{

    }
}