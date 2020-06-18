package com.example.memomemo.Activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.memomemo.R
import kotlinx.android.synthetic.main.activity_password.*

abstract class PasswordActivity : AppCompatActivity(){

    private var cusor = 0
    lateinit var passwordTexts : Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        passwordTexts = arrayOf(et_1_password,et_2_password,et_3_password,et_4_password)

        btn_1_password.setOnClickListener { buttonOnClickListener(1) }
        btn_2_password.setOnClickListener { buttonOnClickListener(2) }
        btn_3_password.setOnClickListener { buttonOnClickListener(3) }
        btn_4_password.setOnClickListener { buttonOnClickListener(4) }
        btn_5_password.setOnClickListener { buttonOnClickListener(5) }
        btn_6_password.setOnClickListener { buttonOnClickListener(6) }
        btn_7_password.setOnClickListener { buttonOnClickListener(7) }
        btn_8_password.setOnClickListener { buttonOnClickListener(8) }
        btn_9_password.setOnClickListener { buttonOnClickListener(9) }
        btn_0_password.setOnClickListener { buttonOnClickListener(0) }
        btn_erase_password.setOnClickListener { eraseButtonOnClickListener() }

        et_4_password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) { onPasswordInputFinish() }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    fun buttonOnClickListener(pressedNum : Int) {
        passwordTexts[cusor].setBackgroundResource(R.drawable.input_password)
        passwordTexts[cusor].setText(pressedNum.toString())
        cusor++
    }

    fun eraseButtonOnClickListener() {
        if(cusor>0)
        {
            cusor--
            passwordTexts[cusor].setText("")
            passwordTexts[cusor].setBackgroundResource(R.drawable.default_password)
        }

    }

    fun setExplanationText(string : String){
        tv_explain_password.setText(string)
    }

    fun getCurrentInput() : String{

        return et_1_password.text.toString() + et_2_password.text.toString() + et_3_password.text.toString() + et_4_password.text.toString()
    }

    fun refreshInput(){
        passwordTexts[0].setBackgroundResource(R.drawable.default_password)
        passwordTexts[1].setBackgroundResource(R.drawable.default_password)
        passwordTexts[2].setBackgroundResource(R.drawable.default_password)
        passwordTexts[3].setBackgroundResource(R.drawable.default_password)
        cusor = -1
    }

    abstract fun onPasswordInputFinish()


}