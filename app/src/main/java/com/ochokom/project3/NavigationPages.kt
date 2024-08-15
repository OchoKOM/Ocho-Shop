package com.ochokom.project3

import androidx.compose.runtime.Composable
import com.ochokom.project3.pages.*
import com.ochokom.project3.ui.theme.ThemeViewModel


data class PageInfo(val route: String, val content: @Composable () -> Unit)


@Composable
fun getNavigationPages(
    themeViewModel: ThemeViewModel,
    homeViewModel: HomeViewModel
): List<PageInfo> {

    return listOf(
        PageInfo("home") { HomePage(homeViewModel) },
        PageInfo("favorite") { FavoritesPage(homeViewModel) },
        PageInfo("add") { AddPage() },
        PageInfo("profile") { ProfilePage() },
        PageInfo("settings") { SettingsPage(themeViewModel) }
    )
}