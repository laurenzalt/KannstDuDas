package at.fhj.kannstdudas.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel

@Composable
fun EditSkillScreen(skillId: String?, navController: NavController, viewModel: SkillViewModel = hiltViewModel()) {
    val skill = skillId?.let { viewModel.getSkill(it) }

    var name by remember { mutableStateOf(skill?.name ?: "") }
    var description by remember { mutableStateOf(skill?.description ?: "") }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Button(onClick = {
            skill?.let {
                viewModel.editSkill(it.copy(name = name, description = description))
                navController.navigate("SkillDetail/${it.id}")
            }
        }) {
            Text("Save Changes")
        }
    }
}
