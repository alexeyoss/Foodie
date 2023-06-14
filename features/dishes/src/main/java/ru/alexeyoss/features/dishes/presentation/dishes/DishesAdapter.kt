package ru.alexeyoss.features.dishes.presentation.dishes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.alexeyoss.features.dishes.databinding.ItemDishesFragmentBinding
import ru.alexeyoss.features.dishes.domain.entities.UiDish

class DishesAdapter(
    private val onClickDish: (UiDish) -> Unit
) : ListAdapter<UiDish, DishesAdapter.DishHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDishesFragmentBinding.inflate(inflater, parent, false)
        return DishHolder(binding, onClickDish)
    }


    override fun onBindViewHolder(holder: DishHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class DishHolder(
        private val binding: ItemDishesFragmentBinding,
        private val onClickDish: (UiDish) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                root.setOnClickListener {
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                    val itemId = currentList[bindingAdapterPosition]
                    onClickDish(itemId)
                }
            }
        }

        fun onBind(item: UiDish) {
            with(binding) {
                Glide.with(itemView.context).load(item.imageUrl).into(cardImageView.imageView)
                title.text = item.name
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UiDish>() {
            override fun areItemsTheSame(oldItem: UiDish, newItem: UiDish): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UiDish, newItem: UiDish): Boolean {
                return oldItem == newItem
            }
        }
    }
}