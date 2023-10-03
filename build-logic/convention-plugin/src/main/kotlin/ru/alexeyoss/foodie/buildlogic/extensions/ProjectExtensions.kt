package ru.alexeyoss.foodie.buildlogic.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Блок конфигурации kotlinOptions
 */
internal fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

/**
 * [KotlinAndroidProjectExtension]
 */
internal val Project.kotlin: KotlinAndroidProjectExtension
    get() = extensions.getByName("kotlin") as KotlinAndroidProjectExtension

internal fun Project.applyPlugin(name: String) {
    pluginManager.apply(name)
}
