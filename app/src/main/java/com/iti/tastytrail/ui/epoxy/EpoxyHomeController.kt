package com.iti.tastytrail.ui.epoxy

import android.annotation.SuppressLint
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.iti.tastytrail.R
import com.iti.tastytrail.data.models.Meal
import com.iti.tastytrail.databinding.HomeHeaderBinding
import com.iti.tastytrail.databinding.ItemDiscoverTitleBinding
import com.iti.tastytrail.databinding.ItemMealBinding
import com.iti.tastytrail.databinding.ItemRecommendedMealBinding
import com.iti.tastytrail.databinding.SearchBarBinding
import com.squareup.picasso.Picasso

class EpoxyHomeController(private val onClicked: (String) -> Unit)
: EpoxyController() {
    override fun buildModels() {
        var userName: String? = null
        var recommendedMeal: Meal? = null
        var discoverMeals: List<Meal> = emptyList()

        // todo should fetch data to complete it
        HeaderEpoxyModel(userName ?: "Guest").id("header").addTo(this)

        SearchBarEpoxyModel("Search any meal...").id("search").addTo(this)

        TodayMealEpoxyModel("Today's Meal").id("today").addTo(this)

        DiscoverTitleEpoxyModel("Discover More").id("discover").addTo(this)

        CarouselModel_()
            .id("discover_carousel")
            .models(
                listOf("Pasta", "Pizza", "Burger", "Salad").map { name ->
                    DiscoverMealEpoxyModel(name).id(name)
                }
            )
            .addTo(this)

    }

    data class HeaderEpoxyModel(
        val userName: String
    ) : ViewBindingKotlinModel<HomeHeaderBinding>(R.layout.home_header) {

        @SuppressLint("SetTextI18n")
        override fun HomeHeaderBinding.bind() {
            // todo add the userName from remote
            greeting.text = "Good Morning,"
            feelinHungry.text = "Feelin' hungry?"
            whatAreWe.text = "What are we cookin' today?"
        }

    }

    data class TodayMealEpoxyModel(
        val todaysMealText : String
    ) : ViewBindingKotlinModel<ItemRecommendedMealBinding>(R.layout.item_recommended_meal) {

        override fun ItemRecommendedMealBinding.bind() {
            todaysMealText.text = "Today's Meal"
            recommendedMealName.text = "Pasta"
            Picasso.get().load("https://static6.depositphotos.com/1014511/575/i/450/depositphotos_5755752-stock-photo-healthy-eating.jpg").into(recommendedMealImage)
            recommendedMealDescription.text = "this is simple dis about the meal dong ti the hell"
        }
    }

    data class SearchBarEpoxyModel(
        val hint: String
    ) : ViewBindingKotlinModel<SearchBarBinding>(R.layout.search_bar) {
        override fun SearchBarBinding.bind() {
            searchBar.hint = hint
        }
    }

    data class DiscoverMealEpoxyModel(
        val name : String
    ) : ViewBindingKotlinModel<ItemMealBinding>(R.layout.item_meal){
        override fun ItemMealBinding.bind() {
           mealName.text = name
        }

    }
    data class DiscoverTitleEpoxyModel(
        val title : String
    ) : ViewBindingKotlinModel<ItemDiscoverTitleBinding>(R.layout.item_discover_title) {
        override fun ItemDiscoverTitleBinding.bind() {
           discoverTitle.text = title
        }

    }
}