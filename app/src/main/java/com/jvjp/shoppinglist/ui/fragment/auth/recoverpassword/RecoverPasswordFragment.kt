package com.jvjp.shoppinglist.ui.fragment.auth.recoverpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.config.ConfigFirebase
import com.jvjp.shoppinglist.databinding.FragmentRecoverPasswordBinding
import com.jvjp.shoppinglist.databinding.FragmentSignUpBinding
import com.jvjp.shoppinglist.model.User
import java.lang.Exception


class RecoverPasswordFragment : Fragment() {
    private var autenticacao: FirebaseAuth? = null
    private lateinit var binding: FragmentRecoverPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecoverPasswordBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRecoveryPasswordEmail!!.setOnClickListener(View.OnClickListener {
            autenticacao = ConfigFirebase().getAutenticacao()
            val textoEmail = binding.emailRecovery!!.text.toString()
            val usuario = User()
            usuario!!.email = textoEmail
            autenticacao!!.sendPasswordResetEmail(textoEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(), "Redefinicão de senha Envia para: $textoEmail",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    } else {
                        var excecao = ""
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthInvalidUserException) {
                            excecao = "Usuário não está cadastrado."
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Toast.makeText(
                            requireContext(), "Usuário não está cadastrado.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        })


        binding.buttonCloseRecoveryPassword.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}