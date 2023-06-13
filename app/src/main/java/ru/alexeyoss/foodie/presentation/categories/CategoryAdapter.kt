package ru.alexeyoss.foodie.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.alexeyoss.foodie.data.model.ui.UiCategory
import ru.alexeyoss.foodie.databinding.ItemCategoriesFragmentBinding

class CategoryAdapter(
    private val onClickCategory: (categoryId: Int) -> Unit
) : ListAdapter<UiCategory, CategoryAdapter.CategoryHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesFragmentBinding.inflate(inflater, parent, false)
        return CategoryHolder(binding, onClickCategory)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.onBind(currentList[position])
    }


    inner class CategoryHolder(
        private val binding: ItemCategoriesFragmentBinding, private val onClickCategory: (categoryId: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                root.setOnClickListener {
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                    onClickCategory(currentList[bindingAdapterPosition].id)
                }
            }
        }

        fun onBind(item: UiCategory) {
            with(binding) {
                Glide.with(itemView.context).load(item.image_url).centerCrop().into(backgroundImage)

                categoryTitle.text = item.name
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UiCategory>() {
            override fun areItemsTheSame(
                oldItem: UiCategory, newItem: UiCategory
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UiCategory, newItem: UiCategory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
