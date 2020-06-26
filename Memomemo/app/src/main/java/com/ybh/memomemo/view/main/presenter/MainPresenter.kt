package com.ybh.memomemo.view.main.presenter

class MainPresenter : MainContract.Presenter {

    private lateinit var view: MainContract.View

    override fun setView(view: MainContract.View) {
        this.view = view
    }

}