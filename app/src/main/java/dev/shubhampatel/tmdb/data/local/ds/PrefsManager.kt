package dev.shubhampatel.tmdb.data.local.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import dev.shubhampatel.tmdb.utility.clear
import dev.shubhampatel.tmdb.utility.getValue
import dev.shubhampatel.tmdb.utility.setValue
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IPrefsManager {
    suspend fun clearPrefs()
    fun isLoggedIn(): Flow<Boolean>
    suspend fun setLoggedIn(status: Boolean)
}

class PrefsManager @Inject constructor(private val ds: DataStore<Preferences>) : IPrefsManager {

    private object Keys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    override suspend fun clearPrefs() {
        ds.clear()
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return ds.getValue(Keys.IS_LOGGED_IN, false)
    }

    override suspend fun setLoggedIn(status: Boolean) {
        ds.setValue(Keys.IS_LOGGED_IN, status)
    }
}