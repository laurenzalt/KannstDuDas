@file:OptIn(ExperimentalMaterial3Api::class)

package at.fhj.kannstdudas.presentation.shared.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.navigation.Screen
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import at.fhj.kannstdudas.presentation.viewmodel.NavigationViewModel

/**
 * at.fhj.kannstdudas.presentation.shared.topbar
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun TopBar(navController: NavHostController, navigationViewModel: NavigationViewModel, authViewModel: AuthViewModel) {
    val logoPainter: Painter = painterResource(id = R.drawable.logo)

    TopAppBar(
        title = {
            Image(
                painter = logoPainter,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    navigationViewModel.selectProfile()
                    navController.navigate(Screen.FavoriteSkill)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite Skills"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(8.dp)
    )
}