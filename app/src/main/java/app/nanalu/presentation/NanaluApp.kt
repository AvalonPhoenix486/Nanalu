package app.nanalu.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.nanalu.AppContainer
import app.nanalu.R
import app.nanalu.presentation.components.PlaceholderScreen
import app.nanalu.presentation.home.HomeRoute
import app.nanalu.presentation.home.HomeViewModel
import app.nanalu.presentation.navigation.TopLevelDestination
import app.nanalu.presentation.theme.NanaluTheme

@Composable
fun NanaluApp(container: AppContainer) {
    NanaluTheme {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        // Cada tela cuida do próprio inset superior para o céu da Início poder ir até a borda.
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                NavigationBar {
                    TopLevelDestination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(destination.icon, contentDescription = null) },
                            label = { Text(stringResource(destination.labelRes)) },
                        )
                    }
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = TopLevelDestination.HOME.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(TopLevelDestination.HOME.route) {
                    val viewModel: HomeViewModel = viewModel(
                        factory = viewModelFactory { initializer { HomeViewModel(container.childProfileRepository) } },
                    )
                    HomeRoute(viewModel)
                }
                composable(TopLevelDestination.HISTORY.route) {
                    PlaceholderScreen(stringResource(R.string.nav_history), stringResource(R.string.history_body))
                }
                composable(TopLevelDestination.STATS.route) {
                    PlaceholderScreen(stringResource(R.string.nav_stats), stringResource(R.string.stats_body))
                }
                composable(TopLevelDestination.CONTENT.route) {
                    PlaceholderScreen(stringResource(R.string.nav_content), stringResource(R.string.content_body))
                }
                composable(TopLevelDestination.PROFILE.route) {
                    PlaceholderScreen(stringResource(R.string.nav_profile), stringResource(R.string.profile_body))
                }
            }
        }
    }
}
