package ru.alexeyoss.core.common.di.scope

import javax.inject.Scope

/**
 * Activity-level dagger scope
 * This scope placed between [PerApplication] and [PerScreen]
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity 