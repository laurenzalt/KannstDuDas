package at.fhj.kannstdudas.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel

@Composable
fun EditSkillScreen(skillId: String, navController: NavController, viewModel: SkillViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.getSkill(skillId)
    }

    val skill by viewModel.skill.collectAsState()
    var name by remember(skill) { mutableStateOf(skill?.name ?: "") }
    var description by remember(skill) { mutableStateOf(skill?.description ?: "") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
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

//    Column {
//        TextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Name") }
//        )
//        TextField(
//            value = description,
//            onValueChange = { description = it },
//            label = { Text("Description") }
//        )
//        Button(onClick = {
//            skill?.let {
//                viewModel.editSkill(it.copy(name = name, description = description))
//                navController.navigate("SkillDetail/${it.id}")
//            }
//        }) {
//            Text("Save Changes")
//        }
//    }
}
