package com.ybh.memomemo.view.password.passwordCheck.presenter

import com.ybh.memomemo.view.password.presenter.PasswordPresenter

class PassWordCheckPresenter : PasswordPresenter(), PassWordCheckContract.Presenter {

    override fun passwordInputFinished() {

        if(password.isPasswordRight()){
            (view as PassWordCheckContract.View).startMainActiviy()
        }else
        {
            password.clearPassword()
            (view as PassWordCheckContract.View).refreshInput()
            (view as PassWordCheckContract.View).setExplanaitionText("비밀번호가 일치하지 않습니다.\n다시 입력해 주세요")
        }

    }

}