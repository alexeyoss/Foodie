package ru.alexeyoss.services.preference.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.alexeyoss.core.common.di.App
import ru.alexeyoss.services.preference.saveOperation
import javax.inject.Inject

// TODO DEBUG
class PermissionsPrefs
@Inject constructor(app: App) {
    private val context: Context = app.getApplicationContext()

    private val Context.dataStore by preferencesDataStore(name = PERMISSION_PREFERENCE_NAME)

    suspend fun setPermissionIsRequested(permission: String, isRequested: Boolean): Boolean {
        return saveOperation(PERMISSION_PREFERENCE_NAME) {
            context.dataStore.edit { pref ->
                pref[booleanPreferencesKey(name = permission)] = isRequested
            }
        }
    }

    suspend fun setPermissionIsGranted(permission: String, isGranted: Boolean): Boolean {
        return saveOperation(PERMISSION_PREFERENCE_NAME) {
            context.dataStore.edit { pref ->
                pref[booleanPreferencesKey(name = permission)] = isGranted
            }
        }
    }

    suspend fun isPermissionRequested(permission: String): Boolean {
        return context.dataStore.data
            .map { pref -> pref[booleanPreferencesKey(permission)] ?: false }
            .first()
    }

    suspend fun isPermissionLastGranted(permission: String): Boolean {
        return context.dataStore.data
            .map { pref -> pref[booleanPreferencesKey(permission)] ?: false }
            .first()
    }

    companion object {
        const val PERMISSION_PREFERENCE_NAME = "permission_preference"
    }

}