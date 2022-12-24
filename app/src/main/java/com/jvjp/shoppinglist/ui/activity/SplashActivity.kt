package com.jvjp.shoppinglist.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.View
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.ActivitySplashBinding


const val SPLASH_TIME_OUT = 5000
const val SPLASH_TIME_TEXT = 1000
const val SPLASH_TIME_IMAGE = 2000

class SplashActivity : AppCompatActivity() {
    private var autenticacao: FirebaseAuth? = null
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide();

        Handler(Looper.getMainLooper()).postDelayed({
            binding.imageView.visibility = View.VISIBLE
        }, SPLASH_TIME_TEXT.toLong())

        Handler(Looper.getMainLooper()).postDelayed({
            autenticacao = ConfigFirebase().getAutenticacao()
            //autenticacao.signOut();
            if (autenticacao!!.currentUser != null) {
                abrirTelaPrincipal()
            }else{
                abrirLogin()
            }

        }, SPLASH_TIME_OUT.toLong())


    }
    fun abrirLogin() {
        startActivity(Intent(this, SignActivity::class.java))
    }

    fun abrirTelaPrincipal() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}