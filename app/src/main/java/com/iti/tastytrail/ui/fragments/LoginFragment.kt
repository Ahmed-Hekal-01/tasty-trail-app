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
import com.iti.tastytrail.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userRepo: UserRepository

    private lateinit var preferenceManager: PreferenceManager
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

        preferenceManager = PreferenceManager(requireContext())

        checkLoginState()

        setupClickListeners()
    }

    private fun checkLoginState() {
        if (preferenceManager.isLoggedIn) {
            navigateToHome()
        }
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            performLogin()
        }

        binding.registerRedirect.setOnClickListener {
            navController.navigate(R.id.action_login_to_register)
        }
    }

    private fun performLogin() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (!validateInput(email, password)) {
            return
        }

        setLoadingState(true)

        coroutineScope.launch {
            try {
                val authenticatedUser = withContext(Dispatchers.IO) {
                    userRepo.authenticateUser(email, password)
                }

                setLoadingState(false)

                if (authenticatedUser != null) {
                    saveLoginState(authenticatedUser, email)

                    showToast("Login successful!")

                    navigateToHome()
                } else {
                    showToast("Invalid credentials")
                }

            } catch (e: Exception) {
                setLoadingState(false)
                showToast("Login failed. Please try again.")
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        when {
            email.isEmpty() || password.isEmpty() -> {
                showToast("Please fill in all fields")
                return false
            }
            password.length < 6 -> {
                showToast("Password must be at least 6 characters")
                return false
            }
        }
        return true
    }

    private fun saveLoginState(user: Any?, email: String) {
        preferenceManager.isLoggedIn = true

        when (user) {
            is com.iti.tastytrail.data.models.User -> {
                preferenceManager.saveUserData(
                    userId = user.id.toString(),
                    email = user.email,
                    name = user.username,
                )
            }
            else -> {
                preferenceManager.userEmail = email
                preferenceManager.userName = email.substringBefore("@")
            }
        }

        saveLoginStateBackwardCompatible()
    }

    private fun saveLoginStateBackwardCompatible() {
        val sharedPref = requireActivity()
            .getSharedPreferences("recipe_prefs", Context.MODE_PRIVATE)

        sharedPref.edit {
            putBoolean("isLoggedIn", true)
        }
    }

    private fun navigateToHome() {
        try {
            navController.navigate(
                R.id.action_login_to_home
            )
        } catch (e: Exception) {
            navController.navigate(R.id.action_login_to_home)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.loginButton.text = if (isLoading) "Logging in..." else "Login"
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel()
        _binding = null
    }
}