package com.jvjp.shoppinglist.helpe

import android.view.View
import com.jvjp.shoppinglist.model.User


interface RecyclerItemClickListener {

    fun onClickListenerItem(item: Any?){
        //optional body
    }

    fun onClickListenerItemWithType(v: View?, item: Any?, type: Int){
        //optional body
    }

    fun onClickListenerUser(user: User){
        //optional body
    }

    fun onClickListenerUAddress(uAddress: User){
        //optional body
    }

}