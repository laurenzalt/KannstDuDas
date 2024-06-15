package at.fhj.kannstdudas.presentation.shared.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.navigation.Screen

/**
 * at.fhj.kannstdudas.presentation.shared.navbar
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun NavBar(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = NavigationItemsProvider.items

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.screen) },
                label = { Text(item.screen.label) },
                icon = { Icon(imageVector = item.icon, contentDescription = null) }
            )
        }
    }
}

data class NavigationItem(
    val screen: Screen,
    val icon: ImageVector
)

object NavigationItemsProvider {
    val items = listOf(
        NavigationItem(screen = Screen.Explore, icon = Icons.Default.Search),
        NavigationItem(screen = Screen.NewSkill, icon = Icons.Default.Add),
        NavigationItem(screen = Screen.MySkills, icon = Icons.Default.Favorite)
    )
}