package ru.alexeyoss.foodie.presentation.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.alexeyoss.foodie.databinding.FragmentCategoriesBinding
import ru.alexeyoss.foodie.presentation.collectOnLifecycle


@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var binding: FragmentCategoriesBinding? = null

    private val viewModel: CategoriesViewModel by viewModels()
    private val categoryAdapter = CategoryAdapter(::onCategoryClick)

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
        viewModel.store.stateFlow
            .map { it.UiCategories }
            .distinctUntilChanged()
            .collectOnLifecycle(this) { categories ->
                categoryAdapter.submitList(categories) // Simple insertion of category
            }
    }

    private fun initRecyclerView() {
        val binding = checkNotNull(binding)
        with(binding) {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = categoryAdapter
                addItemDecoration(MarginItemDecoration(8))
                itemAnimator = null
            }
        }
    }

    private fun onCategoryClick() {
        // TODO category clicked
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}