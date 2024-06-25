package at.fhj.kannstdudas.presentation.screen.home

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.navigation.Screen
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel
import kotlinx.coroutines.launch

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Laurenz Altendorfer on 13/06/2024
 */

@Composable
fun SkillDetailScreen(skillId: String, navController: NavHostController, viewModel: SkillViewModel = hiltViewModel(), userViewModel : AuthViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.getSkill(skillId)
        viewModel.isMySkill(skillId)
        viewModel.isSubscribedToSkill(skillId)
    }

    val skill by viewModel.skill.collectAsState()
    val isMySkill by viewModel.isMySkill.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            skill?.let { validSkill ->
                Text(
                    text = validSkill.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                SkillDescription(validSkill)
                if (isMySkill) {
                    SkillManagementButtons(validSkill, viewModel, navController)
                } else {
                    SubscriptionButton(validSkill, viewModel, userViewModel)
                }
            }
        }
    }
}

@Composable
fun SkillManagementButtons(skill: Skill, viewModel: SkillViewModel, navController: NavHostController) {
    Column {
        Button(
            onClick = {
                viewModel.deleteSkill(skill.id)
                navController.navigate(Screen.Explore)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.delete_skill))
        }
        Button(
            onClick = {
                navController.navigate("EditSkill/${skill.id}") // Assuming you have a route for editing skills
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.edit_skill))
        }
    }
}

@Composable
fun SubscriptionButton(skill: Skill, viewModel: SkillViewModel, userViewModel: AuthViewModel) {
    val isSubscribed by viewModel.isSubscribedToSkill.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(skill.id) {
        viewModel.checkIfSubscribed(skill.id)
    }

    Button(
        onClick = {
            coroutineScope.launch {
                if (isSubscribed) {
                    viewModel.unsubscribeSkill(skill.id)
                } else {
                    viewModel.addSubscribedSkill(skill)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(if (isSubscribed) stringResource(R.string.unsubscribe) else stringResource(R.string.add_to_skills))
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
