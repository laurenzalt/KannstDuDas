@file:OptIn(ExperimentalMaterial3Api::class)

package at.fhj.kannstdudas.presentation.shared.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import at.fhj.kannstdudas.navigation.Screen

/**
 * at.fhj.kannstdudas.presentation.shared.topbar
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun TopBar(navController: NavHostController, screen: String) {
    TopAppBar(
        title = {
            Text(
                text = screen,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            IconButton(onClick = { navController.navigate(Screen.Profile) }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(8.dp)
    )
}
