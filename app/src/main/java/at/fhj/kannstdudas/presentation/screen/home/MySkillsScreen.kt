package at.fhj.kannstdudas.presentation.screen.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.navigation.Screen
import at.fhj.kannstdudas.presentation.viewmodel.SkillsViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.collectAsState

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 13/06/2024
 */

@Composable
fun MySkillsScreen(navController: NavHostController, viewModel: SkillsViewModel = hiltViewModel()) {
    var showMySkills by remember { mutableStateOf(false) }

    Box {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    // TODO: center and just show one of the texts
                    Text("My Skills", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(end = 16.dp))
                    Switch(
                        checked = showMySkills,
                        onCheckedChange = { showMySkills = it }
                    )
                    Text("Subscribed Skills", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 16.dp))
                }

                AnimatedContent(
                    targetState = showMySkills,
                    transitionSpec = {
                        if (!targetState) {
                            (slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500)) + fadeIn(animationSpec = tween(500))).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { -1000 },
                                    animationSpec = tween(500)
                                ) + fadeOut(animationSpec = tween(500))
                            )
                        } else {
                            (slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500)) + fadeIn(animationSpec = tween(500))).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { 1000 },
                                    animationSpec = tween(500)
                                ) + fadeOut(animationSpec = tween(500))
                            )
                        }
                    }, label = ""
                ) { displayMySkills ->
                    if (!displayMySkills) {
                        MySkillList(skills = viewModel.mySkills.collectAsState().value, padding, onSkillClick = {
                            // TODO: make this work with specific Skill
                            navController.navigate(Screen.SkillDetail)
                        })
                    } else {
                        MySkillList(skills = viewModel.subscribedSkills.collectAsState().value, padding, onSkillClick = {
                            // TODO: make this work with specific Skill
                            navController.navigate(Screen.SkillDetail)
                        })
                    }
                }
            }
        }
    }

}

@Composable
fun MySkillList(skills: List<Skill>, padding: PaddingValues, onSkillClick: (Skill) -> Unit) {
    LazyColumn(
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(skills) { skill ->
            MySkillCard(skill, onSkillClick)
        }
    }
}

@Composable
fun MySkillCard(skill: Skill, onSkillClick: (Skill) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = skill.color
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSkillClick(skill) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp)
        ) {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
