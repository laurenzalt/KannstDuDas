package at.fhj.kannstdudas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import at.fhj.kannstdudas.navigation.Screen.Explore.label
import at.fhj.kannstdudas.presentation.screen.home.EditSkillScreen
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
import at.fhj.kannstdudas.presentation.viewmodel.NavigationViewModel

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 13/06/2024
 */

@Composable
fun AppNavGraph (
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    authViewModel: AuthViewModel
) {
    val auth = authViewModel.isSignedIn.collectAsState().value
    val startDestination =
        if (auth) Route.HomeNav else Route.AuthNav

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authNavGraph(navController)
        homeNavGraph(navController, navigationViewModel)
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
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
) {
    navigation<Route.HomeNav>(
        startDestination = Screen.Explore,
    ) {
        composable<Screen.Explore> { HomeLayout(navController, navigationViewModel) { ExploreScreen(navController) } }
        composable<Screen.Profile> { HomeLayout(navController, navigationViewModel) { ProfileScreen(hiltViewModel(), navController) } }
        composable<Screen.NewSkill> { HomeLayout(navController, navigationViewModel) { NewSkillScreen(navController) } }
        composable<Screen.MySkills> { HomeLayout(navController, navigationViewModel) { MySkillsScreen(navController) } }
        composable<Screen.SkillDetail> { HomeLayout(navController, navigationViewModel) { SkillDetailScreen("", navController) } }

        // with IDs
        composable("SkillDetail/{skillId}") { backStackEntry ->
            val skillId = backStackEntry.arguments?.getString("skillId") ?: ""
            HomeLayout(navController, navigationViewModel) { SkillDetailScreen(skillId, navController) }
        }
        composable("EditSkill/{skillId}") { backStackEntry ->
            val skillId = backStackEntry.arguments?.getString("skillId") ?: ""
            HomeLayout(navController, navigationViewModel) { EditSkillScreen(skillId, navController) }
        }
    }
}