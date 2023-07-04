package ru.alexeyoss.features.cart.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import ru.alexeyoss.core.presentation.ToolbarStateHandler
import ru.alexeyoss.core.presentation.ToolbarStates
import ru.alexeyoss.core.presentation.viewBinding
import ru.alexeyoss.features.cart.R
import ru.alexeyoss.features.cart.databinding.FragmentCartBinding

class CartFragment : Fragment(R.layout.fragment_cart), ToolbarStateHandler {
    private val binding by viewBinding<FragmentCartBinding>()


    override fun onAttach(context: Context) {
        // TODO inject CartFragment
        super.onAttach(context)
    }

    override fun getToolbarState(): ToolbarStates {
        return ToolbarStates.LocationView
    }

    companion object {
        fun getNewInstance() = CartFragment()
    }
}