package ru.alexeyoss.services.permission.exceptions

/**
 * Исключение, возникающее, если {@link PermissionRequest#settingsRationalStr} равен null.
 */
class SettingsRationalIsNotProvidedException : RuntimeException("Set settingsRationalStr to non null value.")