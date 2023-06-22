package ru.alexeyoss.features.dishes.presentation.dish_details

import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.presentation.ARG_SCREEN
import ru.alexeyoss.core.presentation.viewBinding
import ru.alexeyoss.features.dishes.R
import ru.alexeyoss.features.dishes.databinding.FragmentDishDetailsDialogBinding
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO

@AndroidEntryPoint
class DishDetailsDialogFragment : DialogFragment(R.layout.fragment_dish_details_dialog) {

    private val binding by viewBinding<FragmentDishDetailsDialogBinding>()

    companion object {
        fun getNewInstance(uiDish: UiDishDTO): DishDetailsDialogFragment {
            return DishDetailsDialogFragment().apply {
                arguments = bundleOf(ARG_SCREEN to uiDish)
            }
        }

    }


}