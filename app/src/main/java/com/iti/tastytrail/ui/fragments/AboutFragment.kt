package com.iti.tastytrail.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iti.tastytrail.R
import com.iti.tastytrail.databinding.FragmentAboutBinding
import com.iti.tastytrail.utils.PreferenceManager
import androidx.core.net.toUri

class AboutFragment : BaseFragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceManager = PreferenceManager(requireContext())

        setupAppInfo()
        setupTeamMembers()
        setupAppDescription()
        setupClickListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setupAppInfo() {
        // Set app version - you can get this from BuildConfig
        try {
            val packageInfo = requireContext().packageManager.getPackageInfo(
                requireContext().packageName, 0
            )
            binding.tvVersion.text = "Version ${packageInfo.versionName}"
        } catch (e: Exception) {
            binding.tvVersion.text = "Version 1.0.0"
        }
    }

    private fun setupTeamMembers() {
        val teamMembers = listOf(
            "HeKaL - Lead Developer",
            "Ahmed Madi - Android Developer",
            "Abdelrahman Wageh - UI/UX Designer",
            "Ahmed El-sheikh - Backend Developer"
        )

        binding.tvMemberFirst.text = teamMembers[0]
        binding.tvMemberSecond.text = teamMembers[1]
        binding.tvMemberThird.text = teamMembers[2]
        binding.tvMemberFourth.text = teamMembers[3]
    }

    private fun setupAppDescription() {
        val appDescription = "Discover delicious recipes from around the world with TastyTrail. " +
                "Search, save, and cook your favorite meals with step-by-step instructions " +
                "powered by TheMealDB API. Built with modern Android development practices " +
                "including MVVM architecture, Room database, Retrofit for API calls, and " +
                "Material Design components."
        binding.tvAboutDescription.text = appDescription
    }

    private fun setupClickListeners() {
        with(binding) {

            btnSettings.setOnClickListener {
                handleSettingsClick()
            }

            btnPrivacyPolicy.setOnClickListener {
                openPrivacyPolicy()
            }

            btnLogout.setOnClickListener {
                showLogoutConfirmationDialog()
            }
        }
    }

    private fun handleSettingsClick() {
        showToast("Settings feature coming soon!")

        // Example of how to navigate to settings if you have it:
        // findNavController().navigate(R.id.action_aboutFragment_to_settingsFragment)
    }

    private fun openPrivacyPolicy() {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                // Replace with your actual privacy policy URL
                data = "https://your-privacy-policy-url.com".toUri()
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Unable to open privacy policy")
        }
    }

    private fun showLogoutConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout? You will need to sign in again.")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun performLogout() {
        try {
            preferenceManager.isLoggedIn = false

            preferenceManager.clearUserData()

            showToast("Logged out successfully")

            navController.navigate(
                R.id.action_aboutFragment_to_loginFragment
            )

        } catch (e: Exception) {
            showToast("Error during logout. Please try again.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}