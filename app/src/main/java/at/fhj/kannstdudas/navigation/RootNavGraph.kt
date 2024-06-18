package at.fhj.kannstdudas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import at.fhj.kannstdudas.presentation.screen.SplashScreen
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 13/06/2024
 */

@Composable
fun RootNavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashNav
    ) {
        composable<Screen.SplashNav> { SplashScreen() }
        authNavGraph(navController)
        composable<Screen.HomeNav> {
            HomeScreen(rememberNavController(), Screen.Explore) }
    }

    LaunchedEffect(viewModel.isSignedIn) {
        viewModel.isSignedIn.collect { isSignedIn ->
            if (isSignedIn) {
                navController.navigate(Screen.HomeNav) {
                    popUpTo<Screen.HomeNav> { inclusive = true }
                }
            } else {
                navController.navigate(Screen.AuthNav) {
                    popUpTo<Screen.AuthNav> { inclusive = true }
                }
            }
        }
    }
}