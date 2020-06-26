package com.ybh.memomemo.view.main.presenter

interface MainContract{

    interface View{

    }

    interface Presenter{
        fun setView(view : View)
    }

}