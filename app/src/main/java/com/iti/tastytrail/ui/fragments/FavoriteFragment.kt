package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentFavoriteBinding

class FavoriteFragment : BaseFragment() {
    private var _binding : FragmentFavoriteBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
