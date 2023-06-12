package ru.alexeyoss.foodie.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alexeyoss.foodie.data.model.ui.UiCategory
import ru.alexeyoss.foodie.databinding.ItemCategoriesFragmentBinding

class CategoryAdapter(
    private val onClickCategory: () -> Unit
) : ListAdapter<UiCategory, CategoryAdapter.CategoryHolder>(diffUtil) {

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

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UiCategory>() {
            override fun areItemsTheSame(
                oldItem: UiCategory, newItem: UiCategory
            ): Boolean {
                return oldItem.category.id == newItem.category.id
            }

            override fun areContentsTheSame(
                oldItem: UiCategory, newItem: UiCategory
            ): Boolean {
                return oldItem.category == newItem.category
            }
        }
    }
}
