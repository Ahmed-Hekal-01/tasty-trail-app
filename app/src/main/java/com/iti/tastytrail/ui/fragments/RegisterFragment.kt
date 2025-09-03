package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment() {
    private var _binding : FragmentRegisterBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
