/*
 * Copyright (c) Juraj Hrivnák. All Rights Reserved unless otherwise explicitly stated.
 */

package teksturepako.pakkuDesktop.app.ui.view.routes.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.michaelbull.result.getError
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.TextArea
import teksturepako.pakkuDesktop.app.ui.PakkuDesktopConstants
import teksturepako.pakkuDesktop.app.ui.component.button.CopyToClipboardButton
import teksturepako.pakkuDesktop.app.ui.view.Nav
import teksturepako.pakkuDesktop.app.ui.viewmodel.ModpackViewModel
import teksturepako.pakkuDesktop.pkui.component.PkUiDialog

@Composable
fun ErrorDialogWindow(navController: NavHostController)
{
    val modpackUiState by ModpackViewModel.modpackUiState.collectAsState()

    val error = modpackUiState.lockFile?.getError() ?: run {
        navController.popBackStack()
        return
    }

    PkUiDialog(
        visible = navController.currentDestination?.route?.contains(Nav.Err.route) == true,
        onDismiss = { navController.popBackStack() },
        title = error::class.simpleName?.let { "Error of type '$it' occurred." },
//        dialogState = rememberDialogState(size = DpSize(800.dp, 600.dp))
    ) {
        Row(
            Modifier.padding(PakkuDesktopConstants.commonPaddingSize),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                FlowRow(
                    verticalArrangement = Arrangement.Center, horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    CopyToClipboardButton(error.rawMessage, Modifier.size(35.dp), useSimpleTooltip = true)
                }
                TextArea(
                    TextFieldState(error.rawMessage),
                    readOnly = true,
                    modifier = Modifier.padding(vertical = 4.dp),
                    textStyle = JewelTheme.consoleTextStyle
                )
            }
        }
    }
}
