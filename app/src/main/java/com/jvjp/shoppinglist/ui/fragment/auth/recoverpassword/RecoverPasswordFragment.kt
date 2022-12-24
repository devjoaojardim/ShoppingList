package com.jvjp.shoppinglist.ui.fragment.auth.recoverpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.databinding.FragmentRecoverPasswordBinding
import com.jvjp.shoppinglist.databinding.FragmentSignUpBinding


class RecoverPasswordFragment : Fragment() {

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

        binding.buttonCloseRecoveryPassword.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}