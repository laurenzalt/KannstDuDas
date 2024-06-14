package at.fhj.kannstdudas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 13/06/2024
 */

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.AuthNav
    ) {
        authNavGraph(navController)
        composable<Screen.HomeNav> {
            HomeScreen(rememberNavController(), Screen.Explore) }
    }
}