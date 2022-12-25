package com.jvjp.shoppinglist.model

import com.google.firebase.database.Exclude

class Date {
    @get:Exclude
    var date: String? = null
    var key: String? = null
}