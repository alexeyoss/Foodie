package ru.alexeyoss.features.dishes.presentation.dishes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.alexeyoss.features.dishes.databinding.ItemDishesFragmentBinding
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO

class DishesAdapter(
    private val onClickDish: (UiDishDTO) -> Unit
) : ListAdapter<UiDishDTO, DishesAdapter.DishHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDishesFragmentBinding.inflate(inflater, parent, false)
        return DishHolder(binding, onClickDish)
    }


    override fun onBindViewHolder(holder: DishHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class DishHolder(
        binding: ItemDishesFragmentBinding,
        private val onClickDish: (UiDishDTO) -> Unit
    ) : BaseViewHolder<ItemDishesFragmentBinding, UiDishDTO>(binding) {
        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onClickDish(item)
            }

        }

        override fun onBind(item: UiDishDTO) {
            super.onBind(item)
            with(binding) {
                Glide.with(itemView.context).load(item.imageUrl).into(cardImageView.imageView)
                title.text = item.name
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UiDishDTO>() {
            override fun areItemsTheSame(oldItem: UiDishDTO, newItem: UiDishDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UiDishDTO, newItem: UiDishDTO): Boolean {
                return oldItem == newItem
            }
        }
    }
}