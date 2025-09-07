package com.iti.tastytrail.ui.epoxy

import android.annotation.SuppressLint
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.iti.tastytrail.R
import com.iti.tastytrail.data.models.Meal
import com.iti.tastytrail.data.models.User
import com.iti.tastytrail.databinding.HomeHeaderBinding
import com.iti.tastytrail.databinding.ItemDiscoverTitleBinding
import com.iti.tastytrail.databinding.ItemMealBinding
import com.iti.tastytrail.databinding.ItemRecommendedMealBinding
import com.iti.tastytrail.databinding.SearchBarBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class EpoxyHomeController(
    private val user: User?,
    private val meal: Meal,
    private val discoverMeals: List<Meal> = emptyList(),
    private val onMealClicked: (String) -> Unit
) : EpoxyController() {

    override fun buildModels() {
        // Header with user greeting
        HeaderEpoxyModel(
            userName = user?.username ?: "Guest",
            greeting = getTimeBasedGreeting()
        ).id("header").addTo(this)

        // Search bar
        SearchBarEpoxyModel("Search any meal...").id("search").addTo(this)

        // Today's recommended meal
        TodayMealEpoxyModel(
            meal = meal,
            onMealClicked = onMealClicked
        ).id("today").addTo(this)

        // Discover section title
        DiscoverTitleEpoxyModel("Discover More").id("discover").addTo(this)

        // Discover meals carousel
        if (discoverMeals.isNotEmpty()) {
            CarouselModel_()
                .id("discover_carousel")
                .models(
                    discoverMeals.map { discoverMeal ->
                        val id = DiscoverMealEpoxyModel(
                            meal = discoverMeal,
                            onMealClicked = onMealClicked
                        ).id(discoverMeal.id)
                        id
                    }
                )
                .addTo(this)
        } else {
            // Show placeholder categories if no discover meals loaded
            CarouselModel_()
                .id("discover_carousel")
                .models(
                    listOf("Pasta", "Pizza", "Burger", "Salad", "Dessert", "Soup").map { category ->
                        DiscoverCategoryEpoxyModel(category).id(category)
                    }
                )
                .addTo(this)
        }
    }

    private fun getTimeBasedGreeting(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning,"
            in 12..16 -> "Good Afternoon,"
            else -> "Good Evening,"
        }
    }

    data class HeaderEpoxyModel(
        val userName: String,
        val greeting: String
    ) : ViewBindingKotlinModel<HomeHeaderBinding>(R.layout.home_header) {

        @SuppressLint("SetTextI18n")
        override fun HomeHeaderBinding.bind() {
            greeting.text = this@HeaderEpoxyModel.greeting
            feelinHungry.text = "feelin' hungry?"
            whatAreWe.text = "What are we cookin' today?"
            userName.text = this@HeaderEpoxyModel.userName
        }
    }

    data class TodayMealEpoxyModel(
        val meal: Meal,
        val onMealClicked: (String) -> Unit
    ) : ViewBindingKotlinModel<ItemRecommendedMealBinding>(R.layout.item_recommended_meal) {

        override fun ItemRecommendedMealBinding.bind() {
            // Bind meal data
            todaysMealText.text = "Today's Meal"
            recommendedMealName.text = meal.title

            // Load meal image
            val imageUrl = meal.imageUrl
            if (imageUrl.isNotEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_recipe) // Add a placeholder
                    .error(R.drawable.ic_recipe) // Add an error image
                    .into(recommendedMealImage)
            }

            // Set description (you might need to truncate this)
            val description = meal.instructions
                .take(100)
            recommendedMealDescription.text = if (description.length >= 100) "$description..." else description

            // Set click listener
            root.setOnClickListener {
                onMealClicked(meal.id)
            }
        }
    }

    data class SearchBarEpoxyModel(
        val hint: String
    ) : ViewBindingKotlinModel<SearchBarBinding>(R.layout.search_bar) {
        override fun SearchBarBinding.bind() {
            searchBar.hint = hint

            // TODO: Add search functionality
            searchBar.setOnClickListener {
                // Navigate to search fragment or expand search
            }
        }
    }

    data class DiscoverMealEpoxyModel(
        val meal: Meal,
        val onMealClicked: (String) -> Unit
    ) : ViewBindingKotlinModel<ItemMealBinding>(R.layout.item_meal) {

        override fun ItemMealBinding.bind() {
            mealName.text = meal.title

            // Load meal image if you have an ImageView in the layout
            val imageUrl = meal.imageUrl
            if (imageUrl.isNotEmpty()) {
                // Assuming you have an ImageView in item_meal layout
                Picasso.get().load(imageUrl).into(mealImage)
            }

            // Set click listener
            root.setOnClickListener {
                onMealClicked(meal.id)
            }
        }
    }

    // For category placeholders when no discover meals are loaded
    data class DiscoverCategoryEpoxyModel(
        val categoryName: String
    ) : ViewBindingKotlinModel<ItemMealBinding>(R.layout.item_meal) {

        override fun ItemMealBinding.bind() {
            mealName.text = categoryName

            // TODO: Add category images
            // You might want to add category-specific images here

            root.setOnClickListener {
                // TODO: Navigate to category page or search by category
            }
        }
    }

    data class DiscoverTitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ItemDiscoverTitleBinding>(R.layout.item_discover_title) {

        override fun ItemDiscoverTitleBinding.bind() {
            discoverTitle.text = title
        }
    }
}