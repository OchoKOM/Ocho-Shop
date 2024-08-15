package com.ochokom.project3.ui.theme

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val DarkColorScheme = darkColorScheme(
    primary = DodgerBlue,
    secondary = WhiteBlue,
    tertiary = WhiteGreen,
    background = BlackBlue,
    onPrimary = Color.White,
    onSecondary = Color(0xFF092d50),
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = DodgerBlue,
    secondary = BlackBlue,
    tertiary = BlackGreen,
    background = WhiteBlue,
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

private val Context.dataStore by preferencesDataStore(name = "theme_preferences")

class ThemePreferences(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode_key")
    }

    val themeModeFlow: Flow<ThemeMode> = dataStore.data.map { preferences ->
        val modeOrdinal = preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.ordinal
        ThemeMode.values()[modeOrdinal]
    }

    suspend fun saveThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.ordinal
        }
    }
}

class ThemeViewModel(private val themePreferences: ThemePreferences) : ViewModel() {
    private val _themeMode = mutableStateOf(ThemeMode.SYSTEM)
    val themeMode: MutableState<ThemeMode> get() = _themeMode

    init {
        viewModelScope.launch {
            themePreferences.themeModeFlow.collect { themeMode ->
                _themeMode.value = themeMode
            }
        }
    }

    fun setThemeMode(themeMode: ThemeMode) {
        _themeMode.value = themeMode
        viewModelScope.launch {
            themePreferences.saveThemeMode(themeMode)
        }
    }
}


enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

@Composable
fun Project3Theme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.LIGHT -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
