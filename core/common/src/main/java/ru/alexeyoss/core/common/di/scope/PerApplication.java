package ru.alexeyoss.core.common.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 * Application-level dagger scope
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
}
