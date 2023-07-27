package ru.alexeyoss.features.cart.presentation.cart

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import ru.alexeyoss.core_ui.presentation.collectOnLifecycle
import ru.alexeyoss.core_ui.presentation.dp
import ru.alexeyoss.core_ui.presentation.itemDecorators.LinearVerticalMarginItemDecoration
import ru.alexeyoss.core_ui.presentation.listeners.BackButtonListener
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStateHolder
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStates
import ru.alexeyoss.core_ui.presentation.viewBinding
import ru.alexeyoss.features.cart.R
import ru.alexeyoss.features.cart.databinding.FragmentCartBinding
import ru.alexeyoss.features.cart.di.CartComponentViewModel
import ru.alexeyoss.features.cart.di.provider.CartComponentDepsProvider
import ru.alexeyoss.features.cart.presentation.CartRouter
import ru.alexeyoss.features.cart.presentation.CartSideEffects
import ru.alexeyoss.features.cart.presentation.cart.adapter.CartAdapter
import ru.alexeyoss.features.cart.presentation.cart.adapter.QuantityOperation
import javax.inject.Inject

class CartFragment : Fragment(R.layout.fragment_cart),
    BackButtonListener,
    ToolbarStateHolder {

    @Inject
    internal lateinit var cartViewModelFactory: Lazy<CartViewModel.Factory>
    private val viewModel by viewModels<CartViewModel> {
        cartViewModelFactory.get()
    }

    @Inject
    lateinit var cartRouter: Lazy<CartRouter>
    private val binding by viewBinding<FragmentCartBinding>()
    private val cartAdapter = CartAdapter(::onQuantityClick)

    override fun onAttach(context: Context) {
        val cartDeps = (context.applicationContext as CartComponentDepsProvider).getCartDeps()

        ViewModelProvider(this@CartFragment).get<CartComponentViewModel>().initCartComponent(cartDeps)
            .inject(this@CartFragment)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initListeners()

        viewModel.initUiCartState()
    }

    private fun initListeners() {
        viewModel.sideEffects.collectOnLifecycle(this@CartFragment) { sideEffects ->
            when (sideEffects) {
                is CartSideEffects.Error -> Unit
                is CartSideEffects.Initial -> Unit
                is CartSideEffects.Loading -> Unit
            }
        }

        viewModel.uiCartScreenState.observe(viewLifecycleOwner) { uiCartState ->
            cartAdapter.submitList(uiCartState.cartItems)

            if (uiCartState.cartItems?.isNotEmpty() == true)
                binding.payBtnWithSum.text = resources.getString(
                    R.string.total_text,
                    uiCartState.totalSum.toString()
                )
            else {
                binding.payBtnWithSum.text = resources.getString(R.string.default_total_text)
            }
        }


    }

    private fun initRecyclerView() = with(binding) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter

            addItemDecoration(
                LinearVerticalMarginItemDecoration(
                    verticalMargin = ITEM_VERTICAL_MARGIN.dp,
                    firstItemTopMargin = ITEM_VERTICAL_MARGIN.dp,
                    lastItemMargin = ITEM_VERTICAL_MARGIN.dp
                )
            )

            itemAnimator = null
        }
    }

    private fun onQuantityClick(quantityOperation: QuantityOperation) {
        viewModel.updateUiCartState(quantityOperation)
    }

    override fun getToolbarState(): ToolbarStates = ToolbarStates.LocationView

    override fun onBackPressed(): Boolean {
        cartRouter.get().goBack()
        return true
    }

    companion object {
        const val ITEM_VERTICAL_MARGIN = 8
        fun getNewInstance() = CartFragment()
    }


}