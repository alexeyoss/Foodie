package ru.alexeyoss.foodie.presentation.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.foodie.databinding.FragmentCategoriesBinding


@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var binding: FragmentCategoriesBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val binding = checkNotNull(binding)
        with(binding) {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                adapter = enterBinAdapter
                addItemDecoration(MarginItemDecoration(8))
                itemAnimator = null
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}