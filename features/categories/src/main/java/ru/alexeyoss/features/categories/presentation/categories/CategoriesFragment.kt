package ru.alexeyoss.features.categories.presentation.categories

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import ru.alexeyoss.core_ui.presentation.listeners.BackButtonListener
import ru.alexeyoss.core_ui.presentation.collectOnLifecycle
import ru.alexeyoss.core_ui.presentation.dp
import ru.alexeyoss.core_ui.presentation.itemDecorators.LinearVerticalMarginItemDecoration
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStateHolder
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStates
import ru.alexeyoss.core_ui.presentation.viewBinding
import ru.alexeyoss.features.categories.R
import ru.alexeyoss.features.categories.databinding.FragmentCategoriesBinding
import ru.alexeyoss.features.categories.di.CategoriesComponentViewModel
import ru.alexeyoss.features.categories.di.provider.CategoriesComponentDepsProvider
import ru.alexeyoss.features.categories.presentation.CategoriesUiState
import ru.alexeyoss.features.categories.presentation.CategoryRouter
import javax.inject.Inject

class CategoriesFragment : Fragment(R.layout.fragment_categories), ToolbarStateHolder, BackButtonListener {

    @Inject
    internal lateinit var categoriesViewModelFactory: Lazy<CategoriesViewModel.Factory>
    private val viewModel by viewModels<CategoriesViewModel> {
        categoriesViewModelFactory.get()
    }

    @Inject
    internal lateinit var categoryRouter: Lazy<CategoryRouter>
    private val binding by viewBinding<FragmentCategoriesBinding>()
    private val categoryAdapter = CategoriesAdapter(::onCategoryClick)


    override fun onAttach(context: Context) {
        val categoriesDeps = (context.applicationContext as CategoriesComponentDepsProvider).getCategoryDeps()

        ViewModelProvider(this@CategoriesFragment).get<CategoriesComponentViewModel>()
            .initCategoriesComponent(categoriesDeps)
            .inject(this@CategoriesFragment)

        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        initRecyclerView()
        initListeners()
        shimmerLayout.startShimmer()

        viewModel.getCategories()
    }

    override fun onPause() {
        shimmerEffectGone()
        super.onPause()
    }

    private fun initListeners() {
        viewModel.categoriesFlow.collectOnLifecycle(this@CategoriesFragment) { categoriesUiState ->
            when (categoriesUiState) {
                is CategoriesUiState.Error -> Unit
                is CategoriesUiState.Initial -> Unit
                is CategoriesUiState.Loading -> Unit
                is CategoriesUiState.Success -> {
                    shimmerEffectGone()
                    categoryAdapter.submitList(categoriesUiState.data)
                }
            }

        }
    }

    private fun shimmerEffectGone() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun initRecyclerView() = with(binding) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = categoryAdapter

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

    private fun onCategoryClick(categoryName: String) {
        categoryRouter.get().launchDishesScreen(categoryName)
    }

    override fun getToolbarState(): ToolbarStates = ToolbarStates.LocationView
    override fun onBackPressed(): Boolean {
        categoryRouter.get().goBack()
        return true
    }

    companion object {
        const val ITEM_VERTICAL_MARGIN = 8

        fun getNewInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

}