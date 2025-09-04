package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentSearchBinding
import com.iti.tastytrail.ui.fragments.BaseFragment

class SearchFragment : BaseFragment() {
    // ...existing code...

    private var _binding : FragmentSearchBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
