/*
 * Copyright (c) Juraj Hrivnák. All Rights Reserved unless otherwise explicitly stated.
 */

package teksturepako.pakkuDesktop.app.ui.view.routes.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import teksturepako.pakkuDesktop.app.ui.viewmodel.ProfileViewModel
import teksturepako.pakkuDesktop.pkui.component.PkUiDialog

@Composable
fun NewModpackDialog(navController: NavHostController)
{
    val profileData by ProfileViewModel.profileData.collectAsState()

    PkUiDialog(
        visible = navController.currentDestination?.route?.contains("new_modpack") == true,
        onDismiss = { navController.popBackStack() },
        title = "Modpack '${profileData.currentProfile?.name}' is not initialized.",
    ) {
        Text(
            text = "Do you want to create a new modpack?",
            modifier = Modifier.padding(vertical = 4.dp)
        )

        FlowRow(
            verticalArrangement = Arrangement.Center, horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedButton(
                onClick = {

                },
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text("Yes")
            }
            DefaultButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text("No")
            }
        }
    }
}