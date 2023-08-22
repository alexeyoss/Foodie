package ru.alexeyoss.foodie.permissions

import android.Manifest
import ru.alexeyoss.core.common.permission.BasePermissionRequest
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.navigation.Screens

object LocationPermissionRequest : BasePermissionRequest() {
    init {
        showPermissionsRational = true
        permissionsRationalStr = R.string.locationRationalText

        showSettingsRational = true
        settingsRationalRoute = Screens.settingsActivity()
    }

    override val permissions: Array<String>
        get() = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
}
