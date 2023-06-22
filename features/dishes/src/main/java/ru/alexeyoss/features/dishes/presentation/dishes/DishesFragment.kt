package ru.alexeyoss.features.dishes.presentation.dishes

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.presentation.ARG_SCREEN
import ru.alexeyoss.core.presentation.collectOnLifecycle
import ru.alexeyoss.core.presentation.dp
import ru.alexeyoss.core.presentation.itemDecorators.GridLayoutMarginItemDecoration
import ru.alexeyoss.core.presentation.itemDecorators.LinearHorizontalMarginItemDecoration
import ru.alexeyoss.core.presentation.viewBinding
import ru.alexeyoss.features.dishes.R
import ru.alexeyoss.features.dishes.databinding.FragmentDishesBinding
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.domain.models.UiFilterDTO
import ru.alexeyoss.features.dishes.presentation.DishesRouter
import ru.alexeyoss.features.dishes.presentation.DishesSideEffects
import ru.alexeyoss.features.dishes.presentation.dishes.adapters.DishesAdapter
import ru.alexeyoss.features.dishes.presentation.dishes.adapters.FiltersAdapter
import javax.inject.Inject

@AndroidEntryPoint
class DishesFragment : Fragment(R.layout.fragment_dishes) {

    @Inject
    lateinit var dishesRouter: DishesRouter

    private val binding by viewBinding<FragmentDishesBinding>()

    private val viewModel: DishesViewModel by viewModels()

    private val dishAdapter = DishesAdapter(::onDishClick)
    private val filterAdapter = FiltersAdapter(::onFilterSelected)


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
            // TODO Debug DishesMarginItemDecoration
            addItemDecoration(
                GridLayoutMarginItemDecoration(
                    spanCount = GRID_SPAN_COUNT.dp,
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
        dishesRouter.launchDishDetailsDialog(uiDish)
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
}