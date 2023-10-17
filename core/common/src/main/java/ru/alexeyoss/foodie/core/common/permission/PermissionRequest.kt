package ru.alexeyoss.foodie.core.common.permission

import com.github.terrakok.cicerone.Screen

/**
 * Базовый класс запроса Runtime Permissions.
 */
abstract class PermissionRequest {

    /**
     * Запрашиваемые разрешения.
     */
    abstract val permissions: Array<String>

    /**
     * Показывать ли объяснение причины запроса разрешений при необходимости.
     */
    var showPermissionsRational: Boolean = false
        protected set

    /**
     * Строка, отображаемая в стандартном диалоге объяснения причины запроса разрешений.
     */
    var permissionsRationalStr: Int? = null
        protected set

    /**
     * Показывать ли объяснение необходимости перехода в настройки приложения.
     */
    var showSettingsRational: Boolean = false
        protected set

    /**
     * Маршрут на экран объяснения необходимости перехода в настройки приложения.
     */
    var settingsRationalRoute: Screen? = null
        protected set
}
