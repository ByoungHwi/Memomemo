package com.ybh.memomemo.view.password.presenter

interface PasswordContract {
    interface View{
        fun setExplanaitionText(string : String)
        fun refreshInput()
        fun showPasswordEntered(cusor : Int)
        fun hidePasswordEntered(cusor : Int)
    }
    interface Presenter<T> {
        fun takeView(view: T)
        fun setModel(savedPassword : String)
        fun numButtonOnClicked(num : Int)
        fun eraseButtonOnClicked()
        fun passwordInputFinished()


    }
}