package com.ochokom.project3.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ochokom.project3.ui.theme.ThemeMode
import com.ochokom.project3.ui.theme.ThemeViewModel
import com.ochokom.project3.R
import com.ochokom.project3.ThemeViewModelFactory

@Composable
fun SettingsPage(
    themeViewModel: ThemeViewModel = viewModel(
        factory = ThemeViewModelFactory(
            LocalContext.current
        )
    )
) {
    val themeModeState = rememberUpdatedState(themeViewModel.themeMode.value)

    val themeMode by themeModeState
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .padding(10.dp, 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings_fill),
                contentDescription = "Settings"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Paramètres",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }

        Column(
            Modifier
                .padding(30.dp, 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Theme de l'application :"
            )
            RadioChoice(
                label = "Clair",
                selected = themeMode == ThemeMode.LIGHT,
                onClick = { themeViewModel.setThemeMode(ThemeMode.LIGHT) }
            )
            RadioChoice(
                label = "Sombre",
                selected = themeMode == ThemeMode.DARK,
                onClick = { themeViewModel.setThemeMode(ThemeMode.DARK) }
            )
            RadioChoice(
                label = "Systeme (par défaut)",
                selected = themeMode == ThemeMode.SYSTEM,
                onClick = { themeViewModel.setThemeMode(ThemeMode.SYSTEM) }
            )
        }
    }
}

@Composable
fun RadioChoice(
    modifier: Modifier = Modifier,
    label: String = "Choix",
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick()
            }
        )
        Text(text = label)
    }
}