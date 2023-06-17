package ru.alexeyoss.features.categories.presentation.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.presentation.collectOnLifecycle
import ru.alexeyoss.core.presentation.toDp
import ru.alexeyoss.features.categories.databinding.FragmentCategoriesBinding
import ru.alexeyoss.features.categories.presentation.CategoriesEvents
import ru.alexeyoss.features.categories.presentation.CategoriesUiState
import ru.alexeyoss.features.categories.presentation.decorators.CategoryMarginItemDecoration

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var binding: FragmentCategoriesBinding? = null

    private val viewModel: CategoriesViewModel by viewModels()
    private val categoryAdapter = CategoriesAdapter(::onCategoryClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initListeners()

        viewModel.setEvent(CategoriesEvents.GetCategories)
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

    private fun initRecyclerView() {
        val binding = checkNotNull(binding)

        with(binding) {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = categoryAdapter
                addItemDecoration(
                    CategoryMarginItemDecoration(
                        verticalMargin = ITEM_VERTICAL_MARGIN.toDp(),
                        firstItemTopMargin = ITEM_VERTICAL_MARGIN.toDp(),
                        lastItemMargin = ITEM_VERTICAL_MARGIN.toDp()
                    )
                )
                itemAnimator = null
            }
        }
    }

    private fun onCategoryClick(categoryId: Int) {
//        findNavController().navigate(R.id.dishesFragment)
//         TODO determinate the certain category dishes
//        when (categoryId) {
//            1 -> Unit
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ITEM_VERTICAL_MARGIN = 8
    }
}