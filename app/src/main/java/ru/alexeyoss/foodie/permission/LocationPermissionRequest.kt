package ru.alexeyoss.foodie.permission

import android.Manifest
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.navigation.Screens
import ru.alexeyoss.services.permission.PermissionRequest

class LocationPermissionRequest : PermissionRequest() {

    init {
        showPermissionsRational = false
        permissionsRationalRoute = Screens.categories()
        permissionsRationalStr = R.string.premissionRationalText.toString()

        showSettingsRational = true
        settingsRationalRoute = Screens.settingsActivity()
        settingsRationalStr = R.string.settingsRationalText.toString()
    }

    override val permissions: Array<String>
        get() = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

}