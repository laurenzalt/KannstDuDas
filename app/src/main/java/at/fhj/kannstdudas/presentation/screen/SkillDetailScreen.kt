package at.fhj.kannstdudas.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.presentation.viewmodel.SkillsViewModel

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Laurenz Altendorfer on 13/06/2024
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SkillDetailScreen(skillId: String, navController: NavHostController, viewModel: SkillsViewModel = hiltViewModel()) {
    val skill = viewModel.getSkillById(skillId) // Implement this method in your ViewModel

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (skill != null) {
                Text("Name: ${skill.name}", style = MaterialTheme.typography.titleLarge)
                Text("Description: ${skill.description}", style = MaterialTheme.typography.bodyLarge)
                // Add more details as needed
            } else {
                Text("Skill not found")
            }
        }
    }
}
