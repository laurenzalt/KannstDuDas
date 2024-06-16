@file:OptIn(ExperimentalMaterial3Api::class)

package at.fhj.kannstdudas.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import at.fhj.kannstdudas.presentation.screen.ExploreScreen
import at.fhj.kannstdudas.presentation.screen.MySkillsScreen
import at.fhj.kannstdudas.presentation.screen.NewSkillScreen
import at.fhj.kannstdudas.presentation.screen.ProfileScreen
import at.fhj.kannstdudas.presentation.screen.SkillDetailScreen
import at.fhj.kannstdudas.presentation.shared.navigation.NavBar
import at.fhj.kannstdudas.presentation.shared.navigation.TopBar

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 12/06/2024
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    startDestination: Screen = Screen.Explore
) {
    //TODO: currentDestination needs to be retrieved properly as Screen object.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.label

    Scaffold(
        topBar = { TopBar(navController = navController, screen = "") },
        bottomBar = { NavBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Explore> { ExploreScreen(navController) }
            composable<Screen.NewSkill> { NewSkillScreen(navController) }
            composable<Screen.MySkills> { MySkillsScreen(navController) }
            composable<Screen.Profile> { ProfileScreen() }
            composable<Screen.SkillDetail> { SkillDetailScreen() }
        }
    }
}