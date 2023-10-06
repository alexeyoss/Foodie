package ru.alexeyoss.foodie.buildlogic.config

import org.gradle.api.JavaVersion

object AppConfig {
    const val APP_DOMAIN = "ru.alexeyoss.foodie"
    const val MIN_SDK = 24
    const val TARGET_SDK = 33
    const val COMPILE_SDK = 33
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    val JVM_TARGET = JavaVersion.VERSION_17

    const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.4.0"

    // Обфускация в релизе
    const val MINIFY_RELEASE = false
}
