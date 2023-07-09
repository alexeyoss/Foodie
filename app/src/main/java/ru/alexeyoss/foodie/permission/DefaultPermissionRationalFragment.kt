package ru.alexeyoss.foodie.permission

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.alexeyoss.core.presentation.ARG_SCREEN
import ru.alexeyoss.core.presentation.viewBinding
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.databinding.FragmentDefaultPermissionRationalBinding

class DefaultPermissionRationalFragment : Fragment(R.layout.fragment_default_permission_rational) {

    private val binding by viewBinding<FragmentDefaultPermissionRationalBinding>()

    override fun onAttach(context: Context) {
//        context.appComponent.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun getNewInstance(categoryName: String): DefaultPermissionRationalFragment {
            return DefaultPermissionRationalFragment().apply {
                arguments = bundleOf(ARG_SCREEN to categoryName)
            }
        }
    }
}