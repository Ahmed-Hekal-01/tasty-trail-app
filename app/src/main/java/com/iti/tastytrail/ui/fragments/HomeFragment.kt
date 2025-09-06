package com.iti.tastytrail.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.iti.tastytrail.R
import com.iti.tastytrail.databinding.FragmentHomeBinding
import com.iti.tastytrail.ui.epoxy.EpoxyHomeController
import com.iti.tastytrail.viewmodels.HomeViewModel

class HomeFragment : BaseFragment() {

    private var _binding : FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var  homeViewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val epoxyHomeController = EpoxyHomeController{ mealId ->
            homeViewModel.onMealSelected(mealId)
            navController.navigate(R.id.action_home_to_detail)
        }
        binding.homeRecyclerView.setControllerAndBuildModels(epoxyHomeController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}