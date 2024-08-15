package com.ochokom.project3


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ochokom.project3.R
import com.ochokom.project3.pages.HomeViewModel
import com.ochokom.project3.ui.theme.Project3Theme
import com.ochokom.project3.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContent {
            MyApp()
        }
    }
}


@Preview
@Composable
private fun MyApp() {
    val navController = rememberNavController()
    val selectedItemState = remember {
        mutableStateOf("home")
    }
    val themeViewModel: ThemeViewModel =
        viewModel(factory = ThemeViewModelFactory(LocalContext.current))
    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModelFactory(LocalContext.current))

    Project3Theme(themeMode = themeViewModel.themeMode.value) {
        Scaffold(
            bottomBar = {
                MyBottomBar(navController, homeViewModel)
            },
            content = { padding ->
                val paddings = padding
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(color = colorScheme.background)
                ) {
                    NavigationGraph(navController, themeViewModel, homeViewModel)
                }
            }
        )

        DisposableEffect(navController) {
            val callback = NavController.OnDestinationChangedListener { _, destination, _ ->
                selectedItemState.value = destination.route ?: "home"
            }
            navController.addOnDestinationChangedListener(callback)
            onDispose {
                navController.removeOnDestinationChangedListener(callback)
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    homeViewModel: HomeViewModel
) {
    val pages = getNavigationPages(themeViewModel, homeViewModel)
    NavHost(navController = navController, startDestination = "home") {
        pages.forEach { page ->
            composable(page.route) { page.content() }
        }
    }
}


data class BottomMenuItem(
    val label: String, val text: String, val icon: Painter, val activeIcon:
    Painter
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "home",
            text = stringResource(R.string.home),
            icon = painterResource(id = R.drawable.home),
            activeIcon = painterResource(id = R.drawable.home_fill)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "favorite",
            text = stringResource(R.string.favorite),
            icon = painterResource(id = R.drawable.favorite),
            activeIcon = painterResource(id = R.drawable.favorite_fill)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "add",
            text = stringResource(R.string.add),
            icon = painterResource(id = R.drawable.add_circle),
            activeIcon = painterResource(id = R.drawable.add_circle_fill)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "profile",
            text = stringResource(R.string.profile),
            icon = painterResource(id = R.drawable.account_circle),
            activeIcon = painterResource(id = R.drawable.account_circle_fill)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "settings",
            text = stringResource(R.string.settings),
            icon = painterResource(id = R.drawable.settings),
            activeIcon = painterResource(id = R.drawable.settings_fill)
        )
    )

    return bottomMenuItemList
}


@Composable
fun MyBottomBar(
    navController: NavController,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val bottomMenuList = prepareBottomMenu()
    val bgColor = colorScheme.onSecondary
    val fontColor = colorScheme.secondary
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedCardIndex by homeViewModel.selectedCardIndex

    if (selectedCardIndex == null) {
        BottomAppBar(
            containerColor = bgColor,
            modifier = modifier.animateContentSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                bottomMenuList.forEach { bottomMenuItem ->
                    val styles = if (bottomMenuItem.label == "add") {
                        Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxSize()
                    } else {
                        Modifier
                    }
                    NavigationBarItem(
                        modifier = styles,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = bgColor,
                            selectedIconColor = fontColor
                        ),
                        selected = (currentRoute == bottomMenuItem.label),
                        onClick = {
                            navController.navigate(bottomMenuItem.label) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            val iconPainter = if (currentRoute == bottomMenuItem.label) {
                                bottomMenuItem.activeIcon
                            } else {
                                bottomMenuItem.icon
                            }
                            val iconModifier = if (bottomMenuItem.label == "add") {
                                Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .padding(bottom = 10.dp)
                            } else {
                                Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                            }
                            Icon(
                                painter = iconPainter,
                                contentDescription = bottomMenuItem.text,
                                modifier = iconModifier,
                                tint = colorScheme.onBackground
                            )
                        },
                        label = {
                            val labelText = if (bottomMenuItem.label != "add") {
                                bottomMenuItem.text
                            } else {
                                ""
                            }
                            Text(
                                text = labelText,
                                fontSize = 9.sp,
                                letterSpacing = 1.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        alwaysShowLabel = true,
                        enabled = true
                    )
                }
            }
        }
    }
}

