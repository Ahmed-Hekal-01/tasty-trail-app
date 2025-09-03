package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment() {
    // ...existing code...

    private var _binding : FragmentAboutBinding? = null

    private val binding get() = _binding


    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}
