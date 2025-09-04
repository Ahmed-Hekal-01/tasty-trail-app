package com.iti.tastytrail.ui.fragments

import androidx.fragment.app.Fragment
import com.iti.tastytrail.MainActivity

abstract class BaseFragment : Fragment() {
    protected val navController by lazy {
        (activity as MainActivity)
    }

}
