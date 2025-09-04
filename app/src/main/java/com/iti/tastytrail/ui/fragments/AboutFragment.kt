package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentAboutBinding
import com.iti.tastytrail.ui.fragments.BaseFragment

class AboutFragment : BaseFragment() {
    // ...existing code...

    private var _binding : FragmentAboutBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
