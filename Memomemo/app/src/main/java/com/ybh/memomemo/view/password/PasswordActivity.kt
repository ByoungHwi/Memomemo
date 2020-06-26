package com.ybh.memomemo.view.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ybh.memomemo.R
import com.ybh.memomemo.view.password.presenter.PasswordContract
import com.ybh.memomemo.view.password.presenter.PasswordPresenter
import kotlinx.android.synthetic.main.activity_password.*

abstract class PasswordActivity : AppCompatActivity(), PasswordContract.View{

    lateinit var presenter: Any
    lateinit var passwordTexts : Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        setPresenter()

        val prefs = getSharedPreferences("MAIN_SHARED_PREF",0)
        (presenter as PasswordPresenter).setModel(prefs.getString("PASSWORD","NO_PASSWORD")?:"NO_PASSWORD")

        passwordTexts = arrayOf(et_1_password,et_2_password,et_3_password,et_4_password)

        btn_1_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(1) }
        btn_2_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(2) }
        btn_3_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(3) }
        btn_4_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(4) }
        btn_5_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(5) }
        btn_6_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(6) }
        btn_7_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(7) }
        btn_8_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(8) }
        btn_9_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(9) }
        btn_0_password.setOnClickListener { (presenter as PasswordPresenter).numButtonOnClicked(0) }
        btn_erase_password.setOnClickListener { (presenter as PasswordPresenter).eraseButtonOnClicked() }



    }

    fun setExplanationText(string : String){
        tv_explain_password.setText(string)
    }

    fun getCurrentInput() : String{

        return et_1_password.text.toString() + et_2_password.text.toString() + et_3_password.text.toString() + et_4_password.text.toString()
    }


    open fun setPresenter(){
        presenter = PasswordPresenter()
        (presenter as PasswordPresenter).takeView(this)
    }

    override fun setExplanaitionText(string: String) {
        tv_explain_password.setText(string)
    }

    override fun showPasswordEntered(cusor: Int) {
        passwordTexts[cusor].setBackgroundResource(R.drawable.input_password)
    }

    override fun hidePasswordEntered(cusor: Int) {
        passwordTexts[cusor].setBackgroundResource(R.drawable.default_password)
    }

    override fun refreshInput() {
        for(passwordText : EditText in passwordTexts) passwordText.setBackgroundResource(R.drawable.default_password)
    }


}