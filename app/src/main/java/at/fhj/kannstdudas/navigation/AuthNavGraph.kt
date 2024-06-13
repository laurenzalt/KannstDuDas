package at.fhj.kannstdudas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import at.fhj.kannstdudas.presentation.screen.ForgotPasswordScreen
import at.fhj.kannstdudas.presentation.screen.SignInScreen
import at.fhj.kannstdudas.presentation.screen.SignUpScreen

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.SignIn) {
    NavHost(navController, startDestination) {
        composable<Screen.SignIn>{ SignInScreen(navController) }
        composable<Screen.SignUp> { SignUpScreen(navController) }
        composable<Screen.ForgotPassword> { ForgotPasswordScreen() }
    }
}