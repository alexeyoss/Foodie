package ru.alexeyoss.features.dishes.presentation.dishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.features.dishes.R
import ru.alexeyoss.features.dishes.databinding.FragmentDishesBinding
import ru.alexeyoss.features.dishes.domain.entities.UiDish

@AndroidEntryPoint
class DishesFragment : Fragment() {

    private var binding: FragmentDishesBinding? = null

    private val viewModel: DishesViewModel by viewModels()
    private val dishAdapter = DishesAdapter(::onDishClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDishesBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initListeners()

        viewModel.getCategoryDishes()
    }


    private fun initListeners() {
//        viewModel.store.stateFlow
//            .map { it.UiDishes }
//            .distinctUntilChanged()
//            .collectOnLifecycle(this) { dishes ->
//                dishAdapter.submitList(dishes)
//            }
    }

    private fun initRecyclerView() {
        val binding = checkNotNull(binding)

        with(binding) {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(
                    context,
                    SPAN_COUNT, GridLayoutManager.VERTICAL, false
                )
                adapter = dishAdapter
                // TODO debug item spacing
//                addItemDecoration(
//                    DishesMarginItemDecoration(
//                        spanCount = SPAN_COUNT,
//                        spacing = SPACING.toPx(),
//                        includeEdge = false
//                    )
//                )
                itemAnimator = null
            }
        }
    }

    private fun onDishClick(uiDish: UiDish) {
        findNavController().navigate(R.id.dishesFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

    companion object {
        const val SPAN_COUNT = 3
        const val SPACING = 8
    }
}