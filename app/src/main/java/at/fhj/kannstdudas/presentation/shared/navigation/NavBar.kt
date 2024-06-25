package at.fhj.kannstdudas.presentation.shared.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.navigation.Screen
import at.fhj.kannstdudas.presentation.viewmodel.NavigationViewModel

/**
 * at.fhj.kannstdudas.presentation.shared.navbar
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun NavBar(navController: NavHostController, viewModel: NavigationViewModel) {
    val selectedItem = viewModel.selectedItem.value
    val profileSelected = viewModel.profileSelected.value
    val items = NavigationItemsProvider.items

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index && !profileSelected,
                onClick = {
                    viewModel.setSelectedItem(index)
                    viewModel.clearProfileSelection()
                    navController.navigate(item.screen)
                },
                label = { Text(stringResource(id = item.label)) },
                icon = { Icon(item.icon, contentDescription = stringResource(id = item.label)) }
            )
        }
    }
}


data class NavigationItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: Int
)

object NavigationItemsProvider {
    val items = listOf(
        NavigationItem(Screen.Explore, Icons.Default.Search, R.string.explore),
        NavigationItem(Screen.NewSkill, Icons.Default.Add, R.string.new_skill),
        NavigationItem(Screen.MySkills, Icons.Default.Favorite, R.string.my_skills)
    )
}