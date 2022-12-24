package com.jvjp.shoppinglist.model

import com.google.firebase.database.Exclude
import com.jvjp.shoppinglist.config.ConfigFirebase

class User {
    @get:Exclude
    var idUsuario: String? = null
    var name: String? = null
    var email: String? = null
    var senha: String? = null


    fun salvar() {
        val firebase = ConfigFirebase().getFirebase()
        if (firebase != null) {
            firebase.child("usuarios")
                .child(idUsuario!!)
                .setValue(this)
        }
    }
}