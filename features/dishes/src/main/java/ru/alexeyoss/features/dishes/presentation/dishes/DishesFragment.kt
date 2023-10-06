package ru.alexeyoss.features.dishes.presentation.dishes

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import ru.alexeyoss.core_ui.presentation.collectOnLifecycle
import ru.alexeyoss.core_ui.presentation.dp
import ru.alexeyoss.core_ui.presentation.itemDecorators.GridLayoutMarginItemDecoration
import ru.alexeyoss.core_ui.presentation.itemDecorators.LinearHorizontalMarginItemDecoration
import ru.alexeyoss.core_ui.presentation.listeners.BackButtonListener
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStateOwner
import ru.alexeyoss.core_ui.presentation.viewBinding.viewBinding
import ru.alexeyoss.features.dishes.di.DishesComponentViewModel
import ru.alexeyoss.features.dishes.di.provider.DishesComponentDepsProvider
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.domain.models.UiFilterDTO
import ru.alexeyoss.features.dishes.presentation.DishesRouter
import ru.alexeyoss.features.dishes.presentation.DishesSideEffects
import ru.alexeyoss.features.dishes.presentation.dish_details.DishDetailsDialogFragment
import ru.alexeyoss.features.dishes.presentation.dish_details.DishDetailsDialogFragment.Companion.ARG_SCREEN
import ru.alexeyoss.features.dishes.presentation.dishes.adapters.DishesAdapter
import ru.alexeyoss.features.dishes.presentation.dishes.adapters.FiltersAdapter
import ru.alexeyoss.foodie.coreui.presentation.toolbar.FoodieToolbarStates
import ru.alexeyoss.foodie.features.dishes.R
import ru.alexeyoss.foodie.features.dishes.databinding.FragmentDishesBinding
import timber.log.Timber
import javax.inject.Inject

class DishesFragment :
    Fragment(R.layout.fragment_dishes),
    ToolbarStateOwner,
    BackButtonListener {

    override fun getToolbarState(): FoodieToolbarStates = FoodieToolbarStates.CustomTitle(args)

    @Inject
    internal lateinit var dishesViewModelFactory: Lazy<DishesViewModel.Factory>
    private val viewModel by viewModels<DishesViewModel> {
        dishesViewModelFactory.get()
    }

    @Inject
    lateinit var dishesRouter: DishesRouter

    private val binding by viewBinding<FragmentDishesBinding>()

    private val dishAdapter = DishesAdapter(::onDishClick)
    private val filterAdapter = FiltersAdapter(::onFilterSelected)

    private val args: String
        get() = arguments?.getString(ARG_SCREEN) ?: throw NullPointerException().also { throwable ->
            Timber.e(throwable, "Args $throwable")
        }

    override fun onAttach(context: Context) {
        val dishesDeps = (context.applicationContext as DishesComponentDepsProvider).getDishesDeps()

        ViewModelProvider(this).get<DishesComponentViewModel>().initDishesComponent(dishesDeps)
            .inject(this@DishesFragment)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerViews()
        initListeners()
    }

    private fun initListeners() {
        viewModel.sideEffects.collectOnLifecycle(this@DishesFragment) { sideEffect ->
            when (sideEffect) {
                is DishesSideEffects.Error -> Unit
                is DishesSideEffects.Initial -> Unit
                is DishesSideEffects.Loading -> Unit
            }
        }

        viewModel.dishListState.observe(viewLifecycleOwner) { dishListState ->
            filterAdapter.submitList(dishListState.filters.toList())
            dishAdapter.submitList(dishListState.filteredDishes)
        }
    }

    private fun initRecyclerViews() = with(binding) {
        dishesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, GRID_SPAN_COUNT, GridLayoutManager.VERTICAL, false)
            adapter = dishAdapter
            itemAnimator = null

            addItemDecoration(
                GridLayoutMarginItemDecoration(
                    spanCount = GRID_SPAN_COUNT,
                    spacing = GRID_SPACING.dp,
                    includeEdge = false
                )
            )
        }

        filtersRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
            addItemDecoration(
                LinearHorizontalMarginItemDecoration(
                    horizontalMargin = FILTERS_MARGIN.dp
                )
            )
            itemAnimator = null
        }
    }

    private fun onDishClick(uiDish: UiDishDTO) {
        DishDetailsDialogFragment.getNewInstance(uiDish).show(parentFragmentManager, null)
    }

    private fun onFilterSelected(uiFilterDTO: UiFilterDTO) {
        viewModel.newFilterSelected(uiFilterDTO)
    }

    companion object {

        fun getNewInstance(categoryName: String): DishesFragment {
            return DishesFragment().apply {
                arguments = bundleOf(ARG_SCREEN to categoryName)
            }
        }

        const val GRID_SPAN_COUNT = 3

        const val GRID_SPACING = 8
        const val FILTERS_MARGIN = 8
    }

    override fun onBackPressed(): Boolean {
        dishesRouter.goBack()
        return true
    }
}
