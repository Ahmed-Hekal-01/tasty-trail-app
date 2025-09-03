package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment() {
    // ...existing code...
    private var _binding : FragmentSplashBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
