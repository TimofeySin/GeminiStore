package com.example.geministore.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.geministore.R
import com.example.geministore.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val loginViewModel =
            ViewModelProvider(this)[LoginViewModel::class.java]

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding!!.Enter.setOnClickListener {
            val login = _binding!!.textLogin.text.toString()
            val pass = _binding!!.textPassword.text.toString()
            loginViewModel.checkLogin(login,pass)
        }


        loginViewModel.resultCheck.observe(viewLifecycleOwner) {
            if (it == "OK") {
                _binding!!.Enter.findNavController().navigate(R.id.nav_order_list)
            }

            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        return root
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
