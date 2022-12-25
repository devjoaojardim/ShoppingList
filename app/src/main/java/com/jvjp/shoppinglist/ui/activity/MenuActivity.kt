package com.jvjp.shoppinglist.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.ActivityMainBinding
import com.jvjp.shoppinglist.databinding.ActivityMenuBinding
import com.jvjp.shoppinglist.helpe.Base64Custom
import com.jvjp.shoppinglist.helpe.Preferences
import com.jvjp.shoppinglist.model.User

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    lateinit var preferences: Preferences
    private val firebaseRef = ConfigFirebase().getFirebase()
    private var valueEventListenerUsuario: ValueEventListener? = null
    private val auth = ConfigFirebase().getAutenticacao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = Preferences(this)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide();

        val emailUsuario = auth!!.currentUser!!.email
        val idUsuario = Base64Custom.codificarBase64(emailUsuario)
        val nameUser = firebaseRef!!.child("usuarios").child(idUsuario!!)
        valueEventListenerUsuario = nameUser!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                binding.nameMenu.text = user!!.name
                binding.emailMenu.text = user.email
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        binding.buttonCloseMenu.setOnClickListener { finish() }
        binding.btnHistoricList.setOnClickListener { startActivity(Intent(this, ListDayItensActivity::class.java)) }

        binding.buttonLogout.setOnClickListener {
            auth!!.signOut()
            preferences.clearUserData()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }
}