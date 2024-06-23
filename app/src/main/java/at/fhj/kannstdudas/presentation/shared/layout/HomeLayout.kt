package at.fhj.kannstdudas.presentation.shared.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.presentation.shared.navigation.NavBar
import at.fhj.kannstdudas.presentation.shared.navigation.TopBar
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import at.fhj.kannstdudas.presentation.viewmodel.NavigationViewModel

/**
 * at.fhj.kannstdudas.presentation.shared.layout
 * Created by Noah Dimmer on 19/06/2024
 */

@Composable
fun HomeLayout(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    authenticationViewModel: AuthViewModel,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopBar(navController, navigationViewModel, authenticationViewModel) },
        bottomBar = { NavBar(navController, navigationViewModel) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            content()
        }
    }
}