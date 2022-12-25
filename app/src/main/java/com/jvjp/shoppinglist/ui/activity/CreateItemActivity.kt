package com.jvjp.shoppinglist.ui.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.ActivityCreateItemBinding
import com.jvjp.shoppinglist.helpe.MoneyTextWatcher
import com.jvjp.shoppinglist.model.Shopping
import java.text.SimpleDateFormat
import java.util.*

class CreateItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateItemBinding
    private var shopping: Shopping? = null
    private var globalUnits: String? = null
    private var globalCategory: String? = null

    private val firebaseRef = ConfigFirebase().getFirebase()

    private val auth = ConfigFirebase().getAutenticacao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide();
        val mLocale = Locale("pt", "BR")
        binding.editPriceProduct.addTextChangedListener(MoneyTextWatcher(binding.editPriceProduct,mLocale))
        initialSpinnes()


        binding.buttonRegistreProduct.setOnClickListener {
            saveShopping()
        }

        binding.buttonCloseMenu.setOnClickListener {
            finish()
        }
    }

    private fun saveShopping() {
        if (validationCamps()) {
            shopping = Shopping()
            val calendar = Calendar.getInstance().time
            val formatt = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = formatt.format(calendar)
            shopping!!.teste = "isso nao vai"
            shopping!!.nameProduct = binding.editNameProduct.text.toString()
            shopping!!.amount = binding.editQtdProduct.text.toString().toInt()
            shopping!!.typeramount = globalUnits
            shopping!!.category = globalCategory
            shopping!!.price = binding.editPriceProduct.text.toString()
            shopping!!.descriptor = binding.editObservationProduct.text.toString()
           // shopping!!.salvar(date.toString())
            shopping!!.salvar(date.toString())
            finish()
        }
    }

    private fun validationCamps(): Boolean {
        val nameProduct = binding.editNameProduct.text.toString()
        val qtdProduct = binding.editQtdProduct.text.toString()

        return if (!nameProduct.isEmpty()) {
            if (!qtdProduct.isEmpty()) {
                true
            } else {
                Toast.makeText(
                    this, "Preencha a Quantidade do produto",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
        } else {
            Toast.makeText(
                this, "Preencha o nome do produto",
                Toast.LENGTH_LONG
            ).show()
            false
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
        binding.spinnerCategory.adapter = adapterCategory

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
                    7-> globalCategory = "Higiene Pessoal"
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

    }


}