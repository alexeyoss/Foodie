package ru.alexeyoss.services.permission

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
     *
     * Если задано true, то при необходимости объяснения будут выполнены следующие действия:
     * - если задан {@link #permissionsRationalRoute}, то будет совершен переход по заданному маршруту;
     * - если {@link #permissionsRationalRoute} не задан, но задан {@link #permissionsRationalStr}, то будет
     * совершен переход на стандартный диалог отображающий заданную строку;
     * - если не задано ни одно из вышеперечисленных свойств, то будет возбуждено исключение
     * [PermissionsRationalIsNotProvidedException].
     */
    var showPermissionsRational: Boolean = false
        protected set

    /**
     * Маршрут на экран объяснения причины запроса разрешений.
     */
    var permissionsRationalRoute: Screen? = null
        protected set

    /**
     * Строка, отображаемая в стандартном диалоге объяснения причины запроса разрешений.
     */
    var permissionsRationalStr: String? = null
        protected set

    /**
     * Показывать ли объяснение необходимости перехода в настройки приложения.
     *
     * Необходимость в отображении диалога возникает, если при предыдущем запросе разрешений было отказано и выбрана
     * опция "Don't ask again".
     *
     * Если задано true, то при необходимости объяснения будут выполнены следующие действия:
     * - если задан {@link #settingsRationalRoute}, то будет совершен переход по заданному маршруту;
     * - если {@link #settingsRationalRoute} не задан, но задан {@link #settingsRationalStr}, то будет
     * совершен переход на стандартный диалог отображающий заданную строку;
     * - если не задано ни одно из вышеперечисленных свойств, то будет возбуждено исключение
     * [SettingsRationalIsNotProvidedException].
     */
    var showSettingsRational: Boolean = false
        protected set

    /**
     * Маршрут на экран объяснения необходимости перехода в настройки приложения.
     */
    var settingsRationalRoute: Screen? = null
        protected set

    /**
     * Строка, отображаемая в стандартном диалоге объяснения необходимости перехода в настройки приложения.
     */
    var settingsRationalStr: String? = null
        protected set
}
