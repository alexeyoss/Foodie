package ru.alexeyoss.features.cart.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.presentation.ToolbarStates
import ru.alexeyoss.core.presentation.ToolbarTitleHandler
import ru.alexeyoss.features.cart.databinding.FragmentCartBinding

@AndroidEntryPoint
class CartFragment : Fragment(), ToolbarTitleHandler {
    private var binding: FragmentCartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

    override fun getToolbarState(): ToolbarStates {
        return ToolbarStates.LOCATION
    }
}