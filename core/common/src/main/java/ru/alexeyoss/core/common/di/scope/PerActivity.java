package ru.alexeyoss.core.common.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Activity-level dagger scope
 * This scope placed between {@link PerApplication} and {@link PerScreen}
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
