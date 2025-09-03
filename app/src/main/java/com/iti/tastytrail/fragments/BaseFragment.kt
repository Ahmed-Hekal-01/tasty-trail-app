package com.iti.tastytrail.fragments

import androidx.fragment.app.Fragment
import com.iti.tastytrail.activities.MainActivity

abstract class BaseFragment : Fragment() {
    protected val navController by lazy {
        (activity as MainActivity)
    }
}
