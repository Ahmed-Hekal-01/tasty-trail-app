package com.iti.tastytrail.fragments

import com.iti.tastytrail.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : BaseFragment() {
    private var _binding : FragmentRecipeDetailBinding? = null

    private val binding get() = _binding


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
