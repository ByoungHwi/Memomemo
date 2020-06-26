package com.ybh.memomemo.view.password.passwordSetting.presenter

import com.ybh.memomemo.view.password.presenter.PasswordPresenter

class PasswordSettingPresenter : PasswordPresenter(), PasswordSettingContract.Presenter{

    var isFirstInput : Boolean = true

    override fun passwordInputFinished() {

        if(isFirstInput){
            password.setCurrentToSaved()
            password.clearPassword()
            (view as PasswordSettingContract.View).refreshInput()
            (view as PasswordSettingContract.View).setExplanaitionText("다시 한번 입력해 주세요")
            isFirstInput = false
        }else{
            if(password.isPasswordRight()){
                (view as PasswordSettingContract.View).savePassword(password.getCurrentPassword())
                (view as PasswordSettingContract.View).finishActivity()
            }
            else{
                password.clearPassword()
                (view as PasswordSettingContract.View).refreshInput()
                (view as PasswordSettingContract.View).setExplanaitionText("비밀번호가 일치하지 않습니다.\n다시 입력해 주세요")
                isFirstInput = true
            }
        }


    }
}