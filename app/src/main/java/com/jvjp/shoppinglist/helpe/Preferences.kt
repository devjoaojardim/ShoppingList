package com.jvjp.shoppinglist.helpe

import android.content.Context
import com.jvjp.shoppinglist.model.User


class Preferences(context: Context?) {

    private var preferences = context?.getSharedPreferences("high.preference", 0)
    private var editor = preferences?.edit()

    operator fun set(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun setInforUserName(name: String) {
        editor?.putString("getDataName", name)
        editor!!.commit()
    }

    fun setInforUserEmail(email: String) {
        editor?.putString("getDataEmail", email)
        editor!!.commit()
    }
    fun getInforUserEmail(): String{
        val email = preferences!!.getString("getDataEmail", "")
        return if (email != null && email.isNotEmpty()) {
            email
        } else null.toString()
    }
    fun getInforUserName(): String{
        val email = preferences!!.getString("getDataName", "")
        return if (email != null && email.isNotEmpty()) {
            email
        } else null.toString()
    }

    fun saveInstanceTokenFcm(key: String?, value: String) {
        editor?.putString(key, value)
        editor!!.commit()
    }

    fun getInstanceTokenFcm(): String {
        return preferences!!.getString("token", "")!!
    }

    fun storeInt(key: String?, value: Int) {
        editor?.putInt(key, value)
        editor!!.commit()
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return preferences!!.getInt(key, defaultValue)
    }


    fun setLogin(enable: Boolean) {
        editor!!.putBoolean("login", enable)
        editor!!.commit()
    }

    fun getLogin(): Boolean {
        return preferences?.getBoolean("login", false)!!
    }

    fun clearUserData() {
        editor?.remove("getData")
        editor?.remove("login")
        editor?.remove("token")
        editor?.remove("arrayNotifyCompare")
        editor!!.commit()
    }


    companion object {
        /**
         * Preferences
         */

        const val ENTERING_FIRST_TIME = "EnteringFirstTime"
    }
}