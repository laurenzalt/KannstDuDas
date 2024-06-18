package at.fhj.kannstdudas.presentation.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.presentation.viewmodel.SkillsViewModel

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Laurenz Altendorfer on 13/06/2024
 */

@Composable
fun SkillDetailScreen(skillId: String, navController: NavHostController, viewModel: SkillsViewModel = hiltViewModel()) {
    val skill = viewModel.getSkillById(skillId)
    val isMySkill = skill?.let { viewModel.isMySkill(it.id) } ?: false
    val isSubscribed = skill?.let { viewModel.isSubscribedSkill(it.id) } ?: false

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            skill?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                SkillDescription(it)
                if (isMySkill) {
                    SkillManagementButtons(it, viewModel, navController)
                } else {
                    SubscriptionButton(it, isSubscribed, viewModel)
                }
            } ?: Text("Skill not found", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun SkillManagementButtons(skill: Skill, viewModel: SkillsViewModel, navController: NavHostController) {
    Column {
        Button(
            onClick = { viewModel.deleteSkill(skill) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Delete Skill")
        }
        Button(
            onClick = {
                navController.navigate("EditSkill/${skill.id}") // Assuming you have a route for editing skills
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Edit Skill")
        }
    }
}

@Composable
fun SubscriptionButton(skill: Skill, isSubscribed: Boolean, viewModel: SkillsViewModel) {
    Button(
        onClick = {
            if (isSubscribed) {
                viewModel.unsubscribeSkill(skill)
            } else {
                viewModel.addSubscribedSkill(skill)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(if (isSubscribed) "Unsubscribe" else "Add to skills")
    }
}

@Composable
fun SkillDescription(skill: Skill) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Text(
            text = skill.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}