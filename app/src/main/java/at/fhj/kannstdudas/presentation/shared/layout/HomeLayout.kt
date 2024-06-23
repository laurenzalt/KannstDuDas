package at.fhj.kannstdudas.presentation.shared.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.presentation.shared.navigation.NavBar
import at.fhj.kannstdudas.presentation.shared.navigation.TopBar
import at.fhj.kannstdudas.presentation.viewmodel.NavigationViewModel

/**
 * at.fhj.kannstdudas.presentation.shared.layout
 * Created by Noah Dimmer on 19/06/2024
 */

@Composable
fun HomeLayout(
    navController: NavHostController,
    viewModel: NavigationViewModel,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopBar(navController, viewModel) },
        bottomBar = { NavBar(navController, viewModel) }
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