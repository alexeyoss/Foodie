package ru.alexeyoss.foodie.presentation.dishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.foodie.databinding.FragmentDishesBinding

@AndroidEntryPoint
class DishesFragment : Fragment() {

    private var binding: FragmentDishesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDishesBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }
}