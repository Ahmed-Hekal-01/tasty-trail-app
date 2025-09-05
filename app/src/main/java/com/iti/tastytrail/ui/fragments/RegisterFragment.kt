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
import com.iti.tastytrail.data.models.User
import com.iti.tastytrail.databinding.FragmentRegisterBinding
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
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var userRepo: UserRepository
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            // todo add better error handling

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = User(
                username = username,
                email = email,
                password = password
            )
            coroutineScope.launch {
                val isRegistered = withContext(Dispatchers.IO) {
                    userRepo.registerUser(user)
                }

                // todo don't forget to nav the user to the home after reg
                if (isRegistered) {
//                    saveLoginState()
                    findNavController().navigate(R.id.action_register_to_login)
                } else {
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.loginRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
    }

    private fun saveLoginState() {
        val sharedPref = requireActivity()
            .getSharedPreferences("recipe_prefs", Context.MODE_PRIVATE)

        sharedPref.edit {
            putBoolean("isLoggedIn", true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        _binding = null
    }
}
