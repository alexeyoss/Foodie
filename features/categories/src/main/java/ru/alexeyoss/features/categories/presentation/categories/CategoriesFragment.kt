package ru.alexeyoss.features.categories.presentation.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.alexeyoss.features.categories.R
import ru.alexeyoss.foodie.databinding.FragmentCategoriesBinding
import ru.alexeyoss.foodie.presentation.categories.decorator.CategoryMarginItemDecoration
import ru.alexeyoss.foodie.presentation.collectOnLifecycle
import ru.alexeyoss.foodie.presentation.utils.toDp

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

        viewModel.getCategories()
    }

    private fun initListeners() {
        viewModel.store.stateFlow.map { it.UiCategories }.distinctUntilChanged()
            .collectOnLifecycle(this) { categories ->
                categoryAdapter.submitList(categories)
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
                        verticalMargin = ru.alexeyoss.features.categories.CategoriesFragment.ITEM_VERTICAL_MARGIN.toDp(),
                        firstItemTopMargin = ru.alexeyoss.features.categories.CategoriesFragment.ITEM_VERTICAL_MARGIN.toDp(),
                        lastItemMargin = ru.alexeyoss.features.categories.CategoriesFragment.ITEM_VERTICAL_MARGIN.toDp()
                    )
                )
                itemAnimator = null
            }
        }
    }

    private fun onCategoryClick(categoryId: Int) {
        findNavController().navigate(R.id.dishesFragment)
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