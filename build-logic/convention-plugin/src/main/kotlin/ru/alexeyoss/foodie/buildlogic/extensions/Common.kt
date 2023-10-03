@file:Suppress("UnstableApiUsage")

package ru.alexeyoss.foodie.buildlogic.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import ru.alexeyoss.foodie.buildlogic.config.AppConfig

/**
 * Настройка Kotlin для модуля.
 */
internal fun Project.configureKotlin(
    commonExtension: CommonExtension<*, *, *, *, *>,
) = commonExtension.apply {
    compileOptions {
        sourceCompatibility = AppConfig.JVM_TARGET
        targetCompatibility = AppConfig.JVM_TARGET
    }

    kotlinOptions {
        // Treat all Kotlin warnings as errors (disabled by default)
        allWarningsAsErrors = properties["warningsAsErrors"] as? Boolean ?: false

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            // Enable experimental APIs
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )

        jvmTarget = AppConfig.JVM_TARGET.toString()
    }
}
