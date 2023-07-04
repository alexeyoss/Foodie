package ru.alexeyoss.features.categories.presentation.categories

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.alexeyoss.core.common.BackButtonListener
import ru.alexeyoss.core.presentation.ToolbarStateHandler
import ru.alexeyoss.core.presentation.ToolbarStates
import ru.alexeyoss.core.presentation.collectOnLifecycle
import ru.alexeyoss.core.presentation.dp
import ru.alexeyoss.core.presentation.itemDecorators.LinearVerticalMarginItemDecoration
import ru.alexeyoss.core.presentation.viewBinding
import ru.alexeyoss.features.categories.R
import ru.alexeyoss.features.categories.databinding.FragmentCategoriesBinding
import ru.alexeyoss.features.categories.di.DaggerCategoriesComponent
import ru.alexeyoss.features.categories.di.provider.CategoriesComponentDepsProvider
import ru.alexeyoss.features.categories.presentation.CategoriesUiState
import ru.alexeyoss.features.categories.presentation.CategoryRouter
import javax.inject.Inject

class CategoriesFragment : Fragment(R.layout.fragment_categories), ToolbarStateHandler, BackButtonListener {


    @Inject
    lateinit var categoryRouter: CategoryRouter
    private val binding by viewBinding<FragmentCategoriesBinding>()
    private val viewModel by viewModels<CategoriesViewModel>()
    private val categoryAdapter = CategoriesAdapter(::onCategoryClick)


    override fun onAttach(context: Context) {
        val deps = (context.applicationContext as CategoriesComponentDepsProvider)
            .getCategoriesDeps()

        DaggerCategoriesComponent.builder()
            .deps(deps)
            .build()
            .inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initListeners()

        viewModel.getCategories()
    }

    private fun initListeners() {
        viewModel.categoriesFlow.collectOnLifecycle(this@CategoriesFragment) { categoriesUiState ->
            when (categoriesUiState) {
                // TODO implement other States
                is CategoriesUiState.Error -> Unit
                is CategoriesUiState.Initial -> Unit
                is CategoriesUiState.Loading -> Unit
                is CategoriesUiState.Success -> categoryAdapter.submitList(categoriesUiState.data)
            }

        }
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
        categoryRouter.launchDishesScreen(categoryName)
    }

    override fun getToolbarState(): ToolbarStates = ToolbarStates.LocationView
    override fun onBackPressed(): Boolean {
        categoryRouter.goBack()
        return true
    }


    companion object {
        const val ITEM_VERTICAL_MARGIN = 8

        fun getNewInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

}