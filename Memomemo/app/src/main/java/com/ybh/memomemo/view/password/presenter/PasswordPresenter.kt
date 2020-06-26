package com.ybh.memomemo.view.password.presenter

import com.ybh.memomemo.view.password.model.Password

open class PasswordPresenter : PasswordContract.Presenter<PasswordContract.View>{

    lateinit var view: Any
    lateinit var password : Password

    override fun takeView(view: PasswordContract.View) {
        this.view = view
    }

    override fun setModel(savedPassword: String) {
        this.password = Password(savedPassword)
    }

    override fun eraseButtonOnClicked() {
        password.erasePassword()
        (view as PasswordContract.View).hidePasswordEntered(password.cusor)
    }

    override fun numButtonOnClicked(num: Int) {
        (view as PasswordContract.View).showPasswordEntered(password.cusor)
        password.putPassword(num)

        if(password.isInputFinished()) { passwordInputFinished() }
    }

    override fun passwordInputFinished() {
        (view as PasswordContract.View).refreshInput()
    }
}