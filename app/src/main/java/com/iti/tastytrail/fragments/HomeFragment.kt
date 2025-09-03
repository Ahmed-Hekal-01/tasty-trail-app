package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private var _binding : FragmentHomeBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
