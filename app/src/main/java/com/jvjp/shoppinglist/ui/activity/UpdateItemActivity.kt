package com.jvjp.shoppinglist.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.ActivityUpdateItemBinding
import com.jvjp.shoppinglist.helpe.Base64Custom
import com.jvjp.shoppinglist.helpe.Preferences
import com.jvjp.shoppinglist.model.Shopping
import com.jvjp.shoppinglist.ui.adapter.AdapterListToday
import java.text.SimpleDateFormat
import java.util.*

class UpdateItemActivity : AppCompatActivity() {
    var globalKey = ""
    lateinit var binding: ActivityUpdateItemBinding
    lateinit var preferences: Preferences
    private val firebaseRef = ConfigFirebase().getFirebase()
    private var valueEventListenerShopping: ValueEventListener? = null
    private var shopping: Shopping? = null
    private var adapterShopping: AdapterListToday? = null
    private var globalUnits: String? = null
    private var globalCategory: String? = null
    private val auth = ConfigFirebase().getAutenticacao()
    private var shoppingRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        if (extras != null) {
            globalKey = extras.getString("key").toString()
        }
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide();
        initialSpinnes()

        println(globalKey + "AQUI")
        val emailUsuario = auth!!.currentUser!!.email
        val idUsuario = Base64Custom.codificarBase64(emailUsuario)
        val calendar = Calendar.getInstance().time
        val formatt = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatt.format(calendar)
        val data = firebaseRef!!.child("shopping").child(idUsuario!!).child(date).child(globalKey)
        valueEventListenerShopping = data!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val shopping = snapshot.getValue(Shopping::class.java)
                binding.editNameProduct2.setText(shopping!!.nameProduct.toString())
                binding.editQtdProduct2.setText(shopping!!.amount.toString())
                binding.editPriceProduct2.setText(shopping!!.price.toString())
                binding.editObservationProduct2.setText(shopping!!.descriptor.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        binding.buttonCloseMenu.setOnClickListener {
            finish()
        }

    }


    private fun initialSpinnes() {
        val units = arrayOf(
            "un",
            "dz",
            "ml",
            "L",
            "Kg",
            "g",
            "Caixa",
            "Embalagem",
            "Galão",
            "Lata",
            "Pacote",
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerUnidade.adapter = adapter

        binding.spinnerUnidade.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> globalUnits = "un"
                        1 -> globalUnits = "dz"
                        2 -> globalUnits = "ml"
                        3 -> globalUnits = "L"
                        4 -> globalUnits = "kg"
                        5 -> globalUnits = "g"
                        6 -> globalUnits = "Caixa"
                        7 -> globalUnits = "Embalagem"
                        8 -> globalUnits = "Galão"
                        9 -> globalUnits = "Lata"
                        10 -> globalUnits = "Pacote"

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        /** Outro Spinner**/

        val category = arrayOf(
            "Sem categoria",
            "Bazar e limpeza",
            "Bebidas",
            "Carnes",
            "Comidas Prontas e Congelados",
            "Frios, Leites e Derivados",
            "Frutas, ovos e verduras",
            "Higiene Pessoal",
            "Importados",
            "Mercearia",
            "Padaria e Sobremesas",
            "Saúde e Beleza",
        )
        val adapterCategory = ArrayAdapter(this, android.R.layout.simple_spinner_item, category)
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory2.adapter = adapterCategory

        binding.spinnerCategory2.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> globalCategory = "Sem categoria"
                        1 -> globalCategory = "Bazar e limpeza"
                        2 -> globalCategory = "Bebidas"
                        3 -> globalCategory = "Carnes"
                        4 -> globalCategory = "Comidas Prontas e Congelados"
                        5 -> globalCategory = "Frios, Leites e Derivados"
                        6 -> globalCategory = "Frutas, ovos e verduras"
                        7 -> globalCategory = "Higiene Pessoal"
                        8 -> globalCategory = "Importados"
                        9 -> globalCategory = "Mercearia"
                        10 -> globalCategory = "Padaria e Sobremesas"
                        11 -> globalCategory = "Saúde e Beleza"

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        binding.buttonRegistreProduct2.setOnClickListener {
            upadateData()
        }

    }
    fun upadateData(){
        val emailUsuario = auth!!.currentUser!!.email
        val idUsuario = Base64Custom.codificarBase64(emailUsuario)
        val calendar = Calendar.getInstance().time
        val formatt = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatt.format(calendar)
        val data = firebaseRef!!.child("shopping").child(idUsuario!!).child(date).child(globalKey)
        data.child("amount").setValue(binding.editQtdProduct2.text.toString().toInt())
        data.child("category").setValue(globalCategory)
        data.child("descriptor").setValue(binding.editObservationProduct2.text.toString())
        data.child("nameProduct").setValue(binding.editNameProduct2.text.toString())
        data.child("price").setValue(binding.editPriceProduct2.text.toString())
        data.child("typeramount").setValue(globalUnits)

        finish()
    }
}