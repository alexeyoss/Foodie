package ru.alexeyoss.features.cart.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.alexeyoss.features.cart.R
import ru.alexeyoss.features.cart.databinding.ItemCartFragmentBinding
import ru.alexeyoss.features.cart.domain.entities.UiCartItem


class CartAdapter(
    private val onQuantityClickListener: (quantityOperation: QuantityOperation) -> Unit
) : ListAdapter<UiCartItem, CartAdapter.CartHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartFragmentBinding.inflate(inflater, parent, false)
        return CartHolder(binding, onQuantityClickListener)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.onBind(currentList[position])
    }


    inner class CartHolder(
        private val binding: ItemCartFragmentBinding,
        private val onQuantityClickListener: (quantityOperation: QuantityOperation) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            with(binding.quantitySelector) {

                minusImageView.setOnClickListener {
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                    onQuantityClickListener(
                        QuantityOperation.Minus(
                            currentList[bindingAdapterPosition],
                            bindingAdapterPosition
                        )
                    )

                }

                plusImageView.setOnClickListener {
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                    onQuantityClickListener(
                        QuantityOperation.Plus(
                            currentList[bindingAdapterPosition],
                            bindingAdapterPosition
                        )
                    )
                }
            }

        }


        fun onBind(uiCartItem: UiCartItem) = with(binding) {
            Glide.with(itemView.context).load(uiCartItem.imageUrl).into(cardImageView.imageView)

            with(titleWithPriceAndWeight) {
                dishTitle.text = uiCartItem.name
                dishPrice.text =
                    itemView.context.resources.getString(R.string.valuedDishPrice, uiCartItem.price.toString())
                dishWeight.text =
                    itemView.context.resources.getString(R.string.valuedDishWeight, uiCartItem.weight.toString())
            }

            quantitySelector.amountText.text = uiCartItem.amount.toString()
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UiCartItem>() {
            override fun areItemsTheSame(
                oldItem: UiCartItem, newItem: UiCartItem
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.amount == newItem.amount
            }

            override fun areContentsTheSame(
                oldItem: UiCartItem, newItem: UiCartItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

