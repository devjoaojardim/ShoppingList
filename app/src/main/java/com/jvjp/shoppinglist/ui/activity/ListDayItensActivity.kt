package com.jvjp.shoppinglist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.ActivityListDayItensBinding
import com.jvjp.shoppinglist.helpe.Base64Custom
import com.jvjp.shoppinglist.helpe.RecyclerItemClickListener
import com.jvjp.shoppinglist.model.Date
import com.jvjp.shoppinglist.model.Shopping
import com.jvjp.shoppinglist.ui.adapter.AdapterListAll
import com.jvjp.shoppinglist.ui.adapter.AdapterListToday
import java.text.SimpleDateFormat
import java.util.*

class ListDayItensActivity : AppCompatActivity() {
    lateinit var binding: ActivityListDayItensBinding
    private val firebaseRef = ConfigFirebase().getFirebase()
    private var shoppingRef: DatabaseReference? = null
    private val listShoppingToday: MutableList<String> = ArrayList()
    private var valueEventListenerUsuario: ValueEventListener? = null
    private var valueEventListenerShopping: ValueEventListener? = null
    private var dateList: String? = null
    private var adapterListAll: AdapterListAll? = null
    private val auth = ConfigFirebase().getAutenticacao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDayItensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide()

        listData()
        adapterListAll = AdapterListAll(listShoppingToday, object : RecyclerItemClickListener{

        }, this)

        val layoutManagerRv: RecyclerView.LayoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvLists.layoutManager = layoutManagerRv
        binding.rvLists.adapter = adapterListAll

        binding.buttonCloseMenu.setOnClickListener { finish() }
    }



    private fun listData() {
        val emailUsuario = auth!!.currentUser!!.email
        val idUsuario = Base64Custom.codificarBase64(emailUsuario)
        val calendar = Calendar.getInstance().time
        val formatt = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatt.format(calendar)
        shoppingRef = firebaseRef!!.child("shopping")
            .child(idUsuario)
        valueEventListenerShopping =
            shoppingRef!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listShoppingToday.clear()
                    for (dados in snapshot.children) {
                        dateList = dados.key
                        //dateList!!.key = dados.key
                        listShoppingToday.add(dateList!!)
                        System.out.println("PROJETO" + dados);
                    }
                    adapterListAll!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}