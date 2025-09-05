package com.iti.tastytrail.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iti.tastytrail.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTeamMembers()
        setupAppDescription()
    }

    private fun setupTeamMembers() {
        val teamMembers = listOf(
            "HeKaL",
            "Ahmed Madi",
            "Abdelrahman Wageh",
            "Ahmed El-sheikh"
        )
        binding.tvMemberFirst.text = teamMembers[0]
        binding.tvMemberSecond.text = teamMembers[1]
        binding.tvMemberThird.text = teamMembers[2]
        binding.tvMemberFourth.text = teamMembers[3]
    }

    private fun setupAppDescription() {
        val appDescription = "This recipe application allows users to discover, search, " +
                "and save their favorite meals using TheMealDB API. It is built with " +
                "modern Android development practices including MVVM architecture, " +
                "Room database, Retrofit for API calls, and Material Design components."

        binding.tvAboutDescription.text = appDescription
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}