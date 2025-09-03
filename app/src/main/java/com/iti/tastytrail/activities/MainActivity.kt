package com.iti.tastytrail.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.iti.tastytrail.R
import com.iti.tastytrail.viewmodels.HomeViewModel

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    val viewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById((R.id.nav_host_fragment)) as NavHostFragment
        navController = navHostFragment.navController
    }
}
