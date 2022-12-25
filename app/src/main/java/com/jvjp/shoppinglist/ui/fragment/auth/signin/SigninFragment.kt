package com.jvjp.shoppinglist.ui.fragment.auth.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.FragmentSigninBinding
import com.jvjp.shoppinglist.model.User
import com.jvjp.shoppinglist.ui.activity.MainActivity
import java.lang.Exception


class SigninFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private var autenticacao: FirebaseAuth? = null
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoginEmail.setOnClickListener {
            val textoEmail = binding.editEmailLogin.text.toString()
            val textoSenha = binding.editSenhaLogin.text.toString()
            if (!textoEmail.isEmpty()) {
                if (!textoSenha.isEmpty()) {
                    user = User()
                    user!!.email = textoEmail
                    user!!.senha = textoSenha
                    validarLogin(user!!.email!!, user!!.senha!!)
                } else {
                    Toast.makeText(
                        requireContext(), "Preencha a senha",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(), "Preencha o email",
                    Toast.LENGTH_LONG
                ).show()
            }

        }


        binding.buttonCreateAccount.setOnClickListener { findNavController().navigate(R.id.action_signinFragment_to_signUpFragment) }
        binding.buttonRecoveryPassword.setOnClickListener { findNavController().navigate(R.id.action_signinFragment_to_recoverPasswordFragment) }
    }

    //Criar metedo para valida a entrada do usuario
    fun validarLogin(email: String, password: String) {
        autenticacao = ConfigFirebase().getAutenticacao()
        autenticacao!!.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                abrirTelaPrincipal()
            } else {
                var excecao = ""
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    excecao = "Usuário não está cadastrado."
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    excecao = "E-mail e senha não correspondem a um usuário cadastrado"
                } catch (e: Exception) {
                    excecao = "Erro ao fazer Login " + e.message
                    e.printStackTrace()
                }
                Toast.makeText(
                    requireContext(), excecao,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    //metodo para abrir a tela Principal
    fun abrirTelaPrincipal() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

}