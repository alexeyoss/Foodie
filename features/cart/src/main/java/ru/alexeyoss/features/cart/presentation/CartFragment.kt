package ru.alexeyoss.features.cart.presentation

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.presentation.ToolbarStateHandler
import ru.alexeyoss.core.presentation.ToolbarStates
import ru.alexeyoss.core.presentation.viewBinding
import ru.alexeyoss.features.cart.R
import ru.alexeyoss.features.cart.databinding.FragmentCartBinding

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), ToolbarStateHandler {
    private val binding by viewBinding<FragmentCartBinding>()

    override fun getToolbarState(): ToolbarStates {
        return ToolbarStates.LocationView
    }

    companion object {
        fun getNewInstance() = CartFragment()
    }
}