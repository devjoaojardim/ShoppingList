package com.jvjp.shoppinglist.model

import com.google.firebase.database.Exclude
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.helpe.Base64Custom

class Shopping {

    @get:Exclude
    var idUsuario: String? = null
    var nameProduct: String? = null
    var amount: String? = null
    var typeramount: String? = null
    var price: String? = null
    var category: String? = null
    var senha: String? = null


    fun salvar() {
        val auth = ConfigFirebase().getAutenticacao()
        val firebase = ConfigFirebase().getFirebase()
        if (firebase != null) {
            val idUsuario = Base64Custom.codificarBase64(auth!!.currentUser!!.email)
            firebase.child("shopping")
                .child(idUsuario!!)
                .push()
                .setValue(this)
        }
    }
}