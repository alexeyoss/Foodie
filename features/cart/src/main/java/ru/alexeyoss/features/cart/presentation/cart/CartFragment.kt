package ru.alexeyoss.features.cart.presentation.cart

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import dagger.Lazy
import ru.alexeyoss.core_ui.presentation.BackButtonListener
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStateHolder
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStates
import ru.alexeyoss.core_ui.presentation.viewBinding
import ru.alexeyoss.features.cart.R
import ru.alexeyoss.features.cart.databinding.FragmentCartBinding
import ru.alexeyoss.features.cart.di.CartComponentViewModel
import ru.alexeyoss.features.cart.di.provider.CartComponentDepsProvider
import ru.alexeyoss.features.cart.presentation.CartRouter
import javax.inject.Inject

class CartFragment : Fragment(R.layout.fragment_cart),
    BackButtonListener,
    ToolbarStateHolder {
    private val binding by viewBinding<FragmentCartBinding>()

    @Inject
    internal lateinit var cartViewModelFactory: Lazy<CartViewModel.Factory>
    private val viewModel by viewModels<CartViewModel> {
        cartViewModelFactory.get()
    }

    @Inject
    lateinit var cartRouter: CartRouter

    override fun onAttach(context: Context) {
        val cartDeps = (context.applicationContext as CartComponentDepsProvider).getCartDeps()

        ViewModelProvider(this@CartFragment).get<CartComponentViewModel>().initCartComponent(cartDeps)
            .inject(this@CartFragment)

        super.onAttach(context)
    }

    override fun getToolbarState(): ToolbarStates {
        return ToolbarStates.LocationView
    }

    companion object {
        fun getNewInstance() = CartFragment()
    }

    override fun onBackPressed(): Boolean {
        cartRouter.goBack()
        return true
    }
}