package com.ochokom.project3

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ochokom.project3.pages.CardData
import com.ochokom.project3.pages.HomeViewModel
import com.ochokom.project3.ui.theme.ThemePreferences
import com.ochokom.project3.ui.theme.ThemeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun convertListToJson(list: List<CardData>): String {
    val gson = Gson()
    val type = object : TypeToken<List<CardData>>() {}.type
    return gson.toJson(list, type)
}

fun convertJsonToList(json: String): List<CardData> {
    val gson = Gson()
    val type = object : TypeToken<List<CardData>>() {}.type
    return gson.fromJson(json, type)
}

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(UserPreferences(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val LIKE_STATE_KEY = stringPreferencesKey("like_state_key")
    }

    val likeStateFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[LIKE_STATE_KEY] ?: ""
    }

    suspend fun saveLikeState(likeState: String) {
        dataStore.edit { preferences ->
            preferences[LIKE_STATE_KEY] = likeState
        }
    }
}
class ThemeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(ThemePreferences(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
