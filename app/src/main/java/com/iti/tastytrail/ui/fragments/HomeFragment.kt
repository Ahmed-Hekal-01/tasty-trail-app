package com.iti.tastytrail.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.iti.tastytrail.R
import com.iti.tastytrail.data.models.Meal
import com.iti.tastytrail.data.models.User
import com.iti.tastytrail.databinding.FragmentHomeBinding
import com.iti.tastytrail.repositories.RecipeRepository
import com.iti.tastytrail.repositories.UserRepository
import com.iti.tastytrail.ui.epoxy.EpoxyHomeController
import com.iti.tastytrail.utils.PreferenceManager
import com.iti.tastytrail.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userRepo: UserRepository
    @Inject
    lateinit var mealRepo: RecipeRepository
    private var dm: List<Meal> = emptyList()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show loading state
        showLoading()

        // Load data and setup UI
        loadHomeData()
    }

    private fun loadHomeData() {
        coroutineScope.launch {
            try {
                // Get current user
                val currentUser: User? = withContext(Dispatchers.IO) {
                    getCurrentUser()
                }

                // Get random meal with proper error handling
                val randomMeal: Meal? = withContext(Dispatchers.IO) {
                    val result = mealRepo.getRandomRecipe()
                    when {
                        result.isSuccess -> result.getOrNull()
                        else -> {
                            // Log error or handle it
                            null
                        }
                    }
                }
                val meals = mutableListOf<Meal>()
                repeat(15) {
                    mealRepo.refreshRecipes()
                    val randomMeal = mealRepo.getRandomRecipe()
                    if (randomMeal.isSuccess) {
                        randomMeal.getOrNull()?.let { meal ->
                            meals.add(meal)
                        }
                    }
                }
                dm = meals
                // Hide loading and setup UI
                hideLoading()

                if (randomMeal != null) {
                    setupEpoxyController(currentUser, randomMeal , dm)
                } else {
                    showError("Failed to load meal data")
                }

            } catch (e: Exception) {
                hideLoading()
                showError("An error occurred: ${e.message}")
            }
        }
    }

    private fun setupEpoxyController(user: User?, meal: Meal , dm: List<Meal>) {
        val epoxyHomeController = EpoxyHomeController(
            user = user,
            meal = meal,
            // You might want to load more meals for discovery section
            discoverMeals = dm // TODO: Load discover meals
        ) { mealId ->
            homeViewModel.onMealSelected(mealId)
            findNavController().navigate(R.id.action_home_to_detail)
        }

        binding.homeRecyclerView.setControllerAndBuildModels(epoxyHomeController)
    }

    private fun showLoading() {
        // Show loading indicator
        binding.homeRecyclerView.visibility = View.GONE
        // If you have a loading view: binding.loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        // Hide loading indicator
        binding.homeRecyclerView.visibility = View.VISIBLE
        // If you have a loading view: binding.loadingView.visibility = View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    suspend fun getCurrentUser(): User? {
        val preferenceManager = PreferenceManager(requireContext())

        return if (preferenceManager.isLoggedIn) {
            // Try to get from local storage first
            preferenceManager.getCurrentUser() ?: run {
                // If not in preferences, try to get from database
                getCurrentUserFromDatabase()
            }
        } else {
            null
        }
    }

    suspend fun getCurrentUserFromDatabase(): User? {
        return try {
            val preferenceManager = PreferenceManager(requireContext())
            val userId = preferenceManager.userId?.toLongOrNull() ?: return null

            // Get from Room database
            val userEntity = userRepo.getUserById(userId.toString())
            userEntity?.let {
                User(
                    id = it.id,
                    email = it.email,
                    username = it.username,
                    password = ""
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel()
        _binding = null
    }
}