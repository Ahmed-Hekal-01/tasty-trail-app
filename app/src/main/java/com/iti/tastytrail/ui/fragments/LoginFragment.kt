package com.iti.tastytrail.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.tastytrail.R
import com.iti.tastytrail.databinding.FragmentLoginBinding
import com.iti.tastytrail.repositories.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userRepo: UserRepository
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            coroutineScope.launch {
                val isAuthenticated = withContext(Dispatchers.IO) {
                    userRepo.authenticateUser(email, password)
                }

                if (isAuthenticated != null) {
                    saveLoginState()
                    findNavController().navigate(R.id.action_login_to_home)
                } else {
                    Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.registerRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }

    private fun saveLoginState() {
        val sharedPref = requireActivity()
            .getSharedPreferences("recipe_prefs", Context.MODE_PRIVATE)

        sharedPref.edit {
            putBoolean("isLoggedIn", true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel()
        _binding = null
    }
}
