package com.ybh.memomemo.view.password.model

class Password(var savedPassword: String) {

    var cusor : Int = 0

    private var currentPassword : Array<String?> = arrayOfNulls<String>(4)

    fun putPassword(num : Int) {
        if(cusor<4){
            currentPassword[cusor] = num.toString()
            cusor++
        }
    }

    fun erasePassword() {
        if(cusor>0){
            cusor--
            currentPassword[cusor] = null
        }
    }

    fun clearPassword() {
        currentPassword[0] = null
        currentPassword[1] = null
        currentPassword[2] = null
        currentPassword[3] = null
        cusor = 0
    }

    fun isPasswordRight() : Boolean {
        return getCurrentPassword()==savedPassword
    }

    fun isInputFinished() : Boolean{
        return cusor > 3
    }

    fun getCurrentPassword() : String {
        var result : String = ""
        for(num:String? in currentPassword) result+=num
        return result
    }

    fun setCurrentToSaved(){
        savedPassword = getCurrentPassword()
    }

}