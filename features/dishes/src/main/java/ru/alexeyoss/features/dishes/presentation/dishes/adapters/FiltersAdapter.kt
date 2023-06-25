package ru.alexeyoss.features.dishes.presentation.dishes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alexeyoss.core.theme.R.color
import ru.alexeyoss.features.dishes.databinding.ItemFiltersFragmentBinding
import ru.alexeyoss.features.dishes.domain.models.UiFilterDTO

class FiltersAdapter(
    private val onFilterSelected: (UiFilterDTO) -> Unit
) : ListAdapter<UiFilterDTO, FiltersAdapter.FilterHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFiltersFragmentBinding.inflate(inflater, parent, false)
        return FilterHolder(binding, onFilterSelected)
    }

    override fun onBindViewHolder(holder: FilterHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class FilterHolder(
        binding: ItemFiltersFragmentBinding, private val onFilterSelected: (UiFilterDTO) -> Unit
    ) : BaseViewHolder<ItemFiltersFragmentBinding, UiFilterDTO>(binding) {

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onFilterSelected(item)
            }
        }

        override fun onBind(item: UiFilterDTO) {
            super.onBind(item)
            with(binding) {
                filterText.text = item.displayText
                filterText.setTextStyle(item.isChecked)
                cardView.setCheckedStyle(item.isChecked)
            }
        }

        private fun TextView.setTextStyle(isChecked: Boolean) {
            val textColor = when (isChecked) {
                true -> resources.getColor(color.white, null)
                false -> resources.getColor(color.black, null)
            }
            setTextColor(textColor)
        }

        private fun CardView.setCheckedStyle(isChecked: Boolean) {
            val backgroundColor = when (isChecked) {
                true -> resources.getColor(color.colorPrimary, null)
                false -> resources.getColor(color.backgroundItem, null)
            }
            setCardBackgroundColor(backgroundColor)
        }
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UiFilterDTO>() {
            override fun areItemsTheSame(oldItem: UiFilterDTO, newItem: UiFilterDTO): Boolean {
                return oldItem.value == newItem.value && oldItem.isChecked == newItem.isChecked
            }

            override fun areContentsTheSame(oldItem: UiFilterDTO, newItem: UiFilterDTO): Boolean {
                return oldItem == newItem
            }
        }
    }
}
