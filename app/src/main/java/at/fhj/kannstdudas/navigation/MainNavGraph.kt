@file:OptIn(ExperimentalMaterial3Api::class)

package at.fhj.kannstdudas.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import at.fhj.kannstdudas.model.Skill
import at.fhj.kannstdudas.presentation.screen.ExploreScreen
import at.fhj.kannstdudas.presentation.screen.MySkillsScreen
import at.fhj.kannstdudas.presentation.screen.NewSkillScreen
import at.fhj.kannstdudas.presentation.screen.ProfileScreen
import at.fhj.kannstdudas.presentation.screen.SkillDetailScreen
import at.fhj.kannstdudas.presentation.shared.navbar.NavBar
import at.fhj.kannstdudas.presentation.shared.topbar.TopBar

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 12/06/2024
 */


@Composable
fun MainNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Explore,
    addSkill: (Skill) -> Unit
) {
    Scaffold(
        topBar = { TopBar(navController = navController, screen = startDestination) },
        bottomBar = { NavBar(navController = navController)}
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Explore> { ExploreScreen(navController) }
            composable<Screen.NewSkill> { NewSkillScreen(navController) }
            composable<Screen.MySkills> { MySkillsScreen() }
            composable<Screen.Profile> { ProfileScreen() }
            composable<Screen.SkillDetail> { SkillDetailScreen() }
        }
    }
}