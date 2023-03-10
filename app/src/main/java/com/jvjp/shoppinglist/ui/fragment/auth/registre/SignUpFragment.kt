package com.jvjp.shoppinglist.ui.fragment.auth.registre

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.FragmentSignUpBinding
import com.jvjp.shoppinglist.databinding.FragmentSigninBinding
import com.jvjp.shoppinglist.helpe.Base64Custom
import com.jvjp.shoppinglist.helpe.Preferences
import com.jvjp.shoppinglist.model.User
import com.jvjp.shoppinglist.ui.activity.MainActivity
import java.lang.Exception


class SignUpFragment : Fragment() {

    private var auth: FirebaseAuth? = null
    private var usuario: User? = null
    lateinit var preferences: Preferences
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = Preferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCloseSignUp.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonRegistreEmail.setOnClickListener {
            val name = binding.editNameRegistre.text.toString()
            val email = binding.editEmailRegistre.text.toString()
            val cpf = binding.editCPF.text.toString()
            val birth = binding.editBirth.text.toString()
            val phone = binding.editPhone.text.toString()
            val password = binding.editPasswordRegistre.text.toString()
            val coPassword = binding.editCoPasswordRegistre.text.toString()
            if (!validatePasswordMatch(
                    password,
                    coPassword
                )
            ) return@setOnClickListener
            when {
                !name.isEmpty() -> {
                    if (!email.isEmpty()) {
                        if (!password.isEmpty()) {
                            if (!coPassword.isEmpty()) {
                                usuario = User()
                                usuario!!.name = name
                                usuario!!.email = email
                                usuario!!.codePerson = cpf
                                usuario!!.birth = birth
                                usuario!!.phone = phone
                                usuario!!.senha = password
                                createUser()

                            }
                        }
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(), "Preencha todos os dados!!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }
    }

    fun createUser() {
        auth = ConfigFirebase().getAutenticacao()
        auth!!.createUserWithEmailAndPassword(
            usuario!!.email.toString(), usuario!!.senha.toString()
        ).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {
                val idUsuario = Base64Custom.codificarBase64(usuario!!.email)
                usuario!!.idUsuario = idUsuario
                usuario!!.salvar()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            } else {
                var excecao = ""
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    excecao = "Digite uma senha mais forte!"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    println("${e.message}")
                    excecao = "Por favor, digite um e-mail v??lido! + ${e.message}"
                } catch (e: FirebaseAuthUserCollisionException) {
                    excecao = "Esse email ja foi cadastrado"
                } catch (e: Exception) {
                    excecao = "Erro ao cadastrar usu??rio: " + e.message
                    e.printStackTrace()
                }
                Toast.makeText(
                    requireContext(), excecao,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun validatePasswordMatch(password1: String?, password2: String?): Boolean {
        return if (password1 == password2) {
            true
        } else {
            Toast.makeText(
                requireContext(),
                "Aten????o, as senhas n??o s??o iguais.",
                Toast.LENGTH_SHORT
            ).show()

            false
        }
    }
}