package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment() {

    private var _binding : FragmentLoginBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
