@file:OptIn(ExperimentalMaterial3Api::class)

package at.fhj.kannstdudas.presentation.shared.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import at.fhj.kannstdudas.navigation.Screen
import at.fhj.kannstdudas.navigation.Screen.Explore.label
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import at.fhj.kannstdudas.presentation.viewmodel.NavigationViewModel
import coil.compose.rememberAsyncImagePainter

/**
 * at.fhj.kannstdudas.presentation.shared.topbar
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun TopBar(navController: NavHostController, navigationViewModel: NavigationViewModel, authViewModel: AuthViewModel) {
    val currentScreenLabel = "test"
    val currentUser = authViewModel.user.collectAsState().value

    TopAppBar(
        title = {
            Text(
                text = currentScreenLabel,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            Box(
            ) {
                IconButton(
                    onClick = {
                        navigationViewModel.selectProfile()
                        navController.navigate(Screen.Profile)
                    }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = currentUser?.profile_picture,
                        ),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(8.dp)
    )
}
