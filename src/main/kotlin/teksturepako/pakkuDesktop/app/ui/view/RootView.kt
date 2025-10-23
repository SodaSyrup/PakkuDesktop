/*
 * Copyright (c) Juraj Hrivnák. All Rights Reserved unless otherwise explicitly stated.
 */

package teksturepako.pakkuDesktop.app.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import teksturepako.pakkuDesktop.app.ui.application.PakkuApplicationScope
import teksturepako.pakkuDesktop.app.ui.view.routes.ModpackView
import teksturepako.pakkuDesktop.app.ui.view.routes.WelcomeView
import teksturepako.pakkuDesktop.app.ui.view.routes.dialogs.ErrorDialogWindow
import teksturepako.pakkuDesktop.app.ui.view.routes.dialogs.NewModpackDialog
import teksturepako.pakkuDesktop.app.ui.view.routes.dialogs.SettingsDialogWindow
import teksturepako.pakkuDesktop.app.ui.viewmodel.ProfileViewModel

@Serializable
sealed class Nav(val route: String) {
    data object Home : Nav("home")
    data object Modpack : Nav("modpack")
    data class Settings(val parent: Nav) : Nav("${parent.route}/settings")
    data class NewModpack(val parent: Nav) : Nav("${parent.route}/new_modpack")
    data object Err : Nav("error")
}

@Composable
fun PakkuApplicationScope.RootView() {
    val profileData by ProfileViewModel.profileData.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        if (profileData.currentProfile != null)
        {
            navController.navigate(Nav.Modpack.route)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Nav.Home.route,
    ) {
        // Home
        composable(Nav.Home.route) {
            WelcomeView(navController)
        }

        // Modpack
        composable(Nav.Modpack.route) {
            ModpackView(navController)
        }

        // Settings
        dialog(Nav.Settings(Nav.Home).route) {
            SettingsDialogWindow(navController)
        }
        dialog(Nav.Settings(Nav.Modpack).route) {
            SettingsDialogWindow(navController)
        }

        // New Modpack
        dialog(Nav.NewModpack(Nav.Home).route) {
            NewModpackDialog(navController)
        }
        dialog(Nav.NewModpack(Nav.Modpack).route) {
            NewModpackDialog(navController)
        }

        // Error
        dialog(Nav.Err.route) {
            ErrorDialogWindow(navController)
        }
    }
}