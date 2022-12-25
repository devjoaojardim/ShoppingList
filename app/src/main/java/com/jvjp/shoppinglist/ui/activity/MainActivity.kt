package com.jvjp.shoppinglist.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.ActivityMainBinding
import com.jvjp.shoppinglist.helpe.Base64Custom
import com.jvjp.shoppinglist.helpe.Preferences
import com.jvjp.shoppinglist.helpe.RecyclerItemClickListener
import com.jvjp.shoppinglist.model.Shopping
import com.jvjp.shoppinglist.model.User
import com.jvjp.shoppinglist.ui.adapter.AdapterListToday
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var preferences: Preferences
    private val firebaseRef = ConfigFirebase().getFirebase()
    private var shoppingRef: DatabaseReference? = null
    private val listShoppingToday: MutableList<Shopping> = ArrayList()
    private var valueEventListenerUsuario: ValueEventListener? = null
    private var valueEventListenerShopping: ValueEventListener? = null
    private var shopping: Shopping? = null
    private var adapterShopping: AdapterListToday? = null
    private val auth = ConfigFirebase().getAutenticacao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = Preferences(this)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide();
        listData()
        swipe()
        adapterShopping =
            AdapterListToday(listShoppingToday, object : RecyclerItemClickListener {
                override fun onClickListenerItem(item: Any?) {
                    super.onClickListenerItem(item)
                    val objectKey = item as Shopping

                    val intent = Intent(this@MainActivity, UpdateItemActivity::class.java)
                    intent.putExtra("key",objectKey.key )
                    startActivity(intent)
                }

            }, this)
        val layoutManagerRv: RecyclerView.LayoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvShoppingToday.layoutManager = layoutManagerRv
        binding.rvShoppingToday.adapter = adapterShopping

        val emailUsuario = auth!!.currentUser!!.email
        val idUsuario = Base64Custom.codificarBase64(emailUsuario)
        val nameUser = firebaseRef!!.child("usuarios").child(idUsuario!!)
        valueEventListenerUsuario = nameUser!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                binding.titleNameUser.text = "Óla,\n${user!!.name} vamos as compras hoje?"
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        binding.menuBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))

        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CreateItemActivity::class.java))
        }
    }

    private fun listData() {
        val emailUsuario = auth!!.currentUser!!.email
        val idUsuario = Base64Custom.codificarBase64(emailUsuario)
        val calendar = Calendar.getInstance().time
        val formatt = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatt.format(calendar)
        shoppingRef = firebaseRef!!.child("shopping")
            .child(idUsuario)
            .child(date)
        valueEventListenerShopping =
            shoppingRef!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listShoppingToday.clear()
                    for (dados in snapshot.children) {
                        shopping = dados.getValue(Shopping::class.java)
                        shopping!!.key = dados.key
                        listShoppingToday.add(shopping!!)
                    }
                    adapterShopping!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun swipe() {
        val itemTouch: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.ACTION_STATE_IDLE
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleterItem(viewHolder)
            }

        }
        ItemTouchHelper(itemTouch).attachToRecyclerView(binding.rvShoppingToday)
    }

    fun deleterItem(viewHolder: RecyclerView.ViewHolder) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Excluir Item da lista")
        alertDialog.setMessage("Você tem certeza que deseja realmente excluir esse item de sua lista?")
        alertDialog.setCancelable(true)
        alertDialog.setPositiveButton("Confirmar") { dialog, which ->
            val position = viewHolder.adapterPosition
            shopping = listShoppingToday[position]
            val emailUsuario = auth!!.currentUser!!.email
            val calendar = Calendar.getInstance().time
            val formatt = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = formatt.format(calendar)
            val idUsuario = Base64Custom.codificarBase64(emailUsuario)
           shoppingRef = firebaseRef!!.child("shopping").child(idUsuario!!).child(date)
            shoppingRef!!.child(shopping!!.key.toString()).removeValue()
            adapterShopping!!.notifyItemRemoved(position)
            println(shopping!!.key.toString())
        }
        alertDialog.setNegativeButton("Cancelar") { dialog, which ->
            Toast.makeText(
                this,
                "Cancelada a opção de Excluir",
                Toast.LENGTH_LONG
            ).show()
            adapterShopping!!.notifyDataSetChanged()
        }
        val alert = alertDialog.create()
        alert.show()

    }


}