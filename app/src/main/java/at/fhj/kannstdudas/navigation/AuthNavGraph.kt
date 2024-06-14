package at.fhj.kannstdudas.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import at.fhj.kannstdudas.presentation.screen.ForgotPasswordScreen
import at.fhj.kannstdudas.presentation.screen.SignInScreen
import at.fhj.kannstdudas.presentation.screen.SignUpScreen

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 12/06/2024
 */

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<Screen.AuthNav>(
        startDestination = Screen.SignIn,
    ) {
        composable<Screen.SignIn> { SignInScreen(navController = navController) }
        composable<Screen.SignUp> { SignUpScreen(navController = navController) }
        composable<Screen.ForgotPassword> { ForgotPasswordScreen() }
    }
}