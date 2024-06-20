package at.fhj.kannstdudas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import at.fhj.kannstdudas.presentation.screen.home.ExploreScreen
import at.fhj.kannstdudas.presentation.screen.home.MySkillsScreen
import at.fhj.kannstdudas.presentation.screen.home.NewSkillScreen
import at.fhj.kannstdudas.presentation.screen.home.ProfileScreen
import at.fhj.kannstdudas.presentation.screen.home.SkillDetailScreen
import at.fhj.kannstdudas.presentation.screen.authentication.ForgotPasswordScreen
import at.fhj.kannstdudas.presentation.screen.authentication.SignInScreen
import at.fhj.kannstdudas.presentation.screen.authentication.SignUpScreen
import at.fhj.kannstdudas.presentation.shared.layout.HomeLayout
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 13/06/2024
 */

@Composable
fun AppNavGraph (
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val auth = viewModel.isSignedIn.collectAsState().value
    val startDestination =
        if (auth) Route.HomeNav else Route.AuthNav

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authNavGraph(navController)
        homeNavGraph(navController)
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<Route.AuthNav>(
        startDestination = Screen.SignIn,
    ) {
        composable<Screen.SignIn> { SignInScreen(navController) }
        composable<Screen.SignUp> { SignUpScreen(navController) }
        composable<Screen.ForgotPassword> { ForgotPasswordScreen(navController) }
    }
}

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    navigation<Route.HomeNav>(
        startDestination = Screen.Explore,
    ) {
        composable<Screen.Explore> { HomeLayout(navController) { ExploreScreen(navController) } }
        composable<Screen.Profile> { HomeLayout(navController) { ProfileScreen(hiltViewModel(), navController) } }
        composable<Screen.NewSkill> { HomeLayout(navController) { NewSkillScreen(navController) } }
        composable<Screen.MySkills> { HomeLayout(navController) { MySkillsScreen(navController) } }
        composable<Screen.SkillDetail> { HomeLayout(navController) { SkillDetailScreen("", navController) } }
    }
}