package ru.alexeyoss.core.common.di.scope

import javax.inject.Scope

/**
 * Service-level dagger scope
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerService