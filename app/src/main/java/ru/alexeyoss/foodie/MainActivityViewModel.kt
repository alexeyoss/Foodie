package ru.alexeyoss.foodie

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import ru.alexeyoss.services.permission.PermissionManager
import javax.inject.Inject

class MainActivityViewModel
@Inject
constructor(
    private val permissionManager: PermissionManager,
    private val router: Router
) : ViewModel() {

    fun requestLocationPermission() {
        /** Request permission and Navigate */
    }
}