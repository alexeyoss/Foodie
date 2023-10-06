package ru.alexeyoss.foodie.core.common.di.scope

import javax.inject.Scope

/**
 * Application-level dagger scope
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication 