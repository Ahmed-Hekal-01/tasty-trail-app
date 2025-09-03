package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment() {
    // ...existing code...

    private var _binding : FragmentSearchBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
