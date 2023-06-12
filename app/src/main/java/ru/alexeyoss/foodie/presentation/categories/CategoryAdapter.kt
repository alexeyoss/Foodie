package ru.alexeyoss.foodie.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alexeyoss.foodie.data.model.ui.UiDish
import ru.alexeyoss.foodie.databinding.ItemCategoriesFragmentBinding

class CategoryAdapter(
    private val onClickCategory: () -> Unit
) : ListAdapter<UiDish, CategoryAdapter.CategoryHolder>() {

    inner class CategoryHolder(
        binding: ItemCategoriesFragmentBinding, private val onClickCategory: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesFragmentBinding.inflate(inflater, parent, false)
        return CategoryHolder(binding, onClickCategory)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
