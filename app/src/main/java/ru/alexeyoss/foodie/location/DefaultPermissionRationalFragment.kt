package ru.alexeyoss.foodie.location

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.presentation.ARG_SCREEN
import ru.alexeyoss.core.presentation.viewBinding
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.databinding.FragmentDefaultPermissionRationalBinding

@AndroidEntryPoint
class DefaultPermissionRationalFragment : Fragment(R.layout.fragment_default_permission_rational) {

    private val binding by viewBinding<FragmentDefaultPermissionRationalBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun getNewInstance(categoryName: String): DefaultPermissionRationalFragment {
            return DefaultPermissionRationalFragment().apply {
                arguments = bundleOf(ARG_SCREEN to categoryName)
            }
        }
    }
}