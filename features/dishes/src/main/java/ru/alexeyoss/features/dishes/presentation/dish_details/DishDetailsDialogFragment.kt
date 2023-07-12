package ru.alexeyoss.features.dishes.presentation.dish_details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import ru.alexeyoss.core_ui.presentation.fragment.ARG_SCREEN
import ru.alexeyoss.core_ui.presentation.BackButtonListener
import ru.alexeyoss.core_ui.presentation.viewBinding
import ru.alexeyoss.features.dishes.R
import ru.alexeyoss.features.dishes.databinding.FragmentDishDetailsDialogBinding
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import timber.log.Timber

class DishDetailsDialogFragment :
    DialogFragment(R.layout.fragment_dish_details_dialog),
    BackButtonListener {

    private val binding by viewBinding<FragmentDishDetailsDialogBinding>()
    override fun getTheme(): Int = ru.alexeyoss.core_ui.theme.R.style.Theme_Foodie_Dialog_CustomSize

    @Suppress("DEPRECATION")
    private val args: UiDishDTO
        get() = arguments?.getParcelable(ARG_SCREEN) ?: throw NullPointerException().also { throwable ->
            Timber.e(
                throwable, "Args $throwable"
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        val uiDish = args
        with(dishInfo) {
            dishTitle.text = uiDish.name
            dishPrice.text =
                resources.getString(ru.alexeyoss.core_ui.presentation.R.string.valuedDishPrice, uiDish.price.toString())
            dishWeight.text =
                resources.getString(
                    ru.alexeyoss.core_ui.presentation.R.string.valuedDishWeight,
                    uiDish.weight.toString()
                )
        }
        dishDescription.text = uiDish.description

        Glide.with(requireContext()).load(uiDish.imageUrl).into(cardImageView.imageView)
    }

    private fun initListeners() = with(binding) {
        favCloseButtons.closeBtn.setOnClickListener { dismiss() }
        // TODO logic with liked
        favCloseButtons.favoriteBtn.setOnClickListener { Unit }
    }

    companion object {
        fun getNewInstance(uiDish: UiDishDTO): DishDetailsDialogFragment {
            return DishDetailsDialogFragment().apply {
                arguments = bundleOf(ARG_SCREEN to uiDish)
            }
        }

    }


    override fun onBackPressed(): Boolean {
        dismiss()
        return true
    }


}