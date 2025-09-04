package com.iti.tastytrail.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.tastytrail.R
import com.iti.tastytrail.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = requireActivity()
                .getSharedPreferences("recipe_prefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

            if (isLoggedIn) {
                findNavController().navigate(
                    R.id.action_splash_to_home
                )
            } else {
                findNavController().navigate(
                    R.id.action_splash_to_login
                )
            }
        }, 1500)

        //cancelLoginState()

    }
    private fun cancelLoginState() {
        val sharedPref = requireActivity()
            .getSharedPreferences("recipe_prefs", Context.MODE_PRIVATE)

        sharedPref.edit {
            putBoolean("isLoggedIn", false)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
