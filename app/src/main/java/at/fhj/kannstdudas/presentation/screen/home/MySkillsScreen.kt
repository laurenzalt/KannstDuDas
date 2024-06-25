package at.fhj.kannstdudas.presentation.screen.home

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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 13/06/2024
 */

@Composable
fun MySkillsScreen(navController: NavHostController, viewModel: SkillViewModel = hiltViewModel(), userViewModel : AuthViewModel = hiltViewModel()) {
    var showSubscribedSkills by remember { mutableStateOf(false) }
    val subscribedSkills by viewModel.subscribedSkills.collectAsState()
    val mySkills by viewModel.mySkills.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSubscribedSkills()
        viewModel.getSkillsByUser()
    }



    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top
        ) {
            ToggleButton(showSubscribedSkills, onToggleChanged = { showSubscribedSkills = it })

            if (showSubscribedSkills) {
                MySkillList(subscribedSkills, padding, onSkillClick = { skill ->
                    navController.navigate("SkillDetail/${skill.id}")
                })
            } else {
                MySkillList(mySkills, padding, onSkillClick = { skill ->
                    navController.navigate("SkillDetail/${skill.id}")
                })
            }
        }
    }
}

@Composable
fun ToggleButton(showSubscribedSkills: Boolean, onToggleChanged: (Boolean) -> Unit) {
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            stringResource(R.string.my_skills),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (!showSubscribedSkills) activeColor else inactiveColor,
                fontWeight = if (!showSubscribedSkills) FontWeight.Bold else FontWeight.Normal
            ),
            modifier = Modifier
                .clickable { onToggleChanged(false) }
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
        Switch(
            checked = showSubscribedSkills,
            onCheckedChange = onToggleChanged,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            stringResource(R.string.subscribed_skills),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (showSubscribedSkills) activeColor else inactiveColor,
                fontWeight = if (showSubscribedSkills) FontWeight.Bold else FontWeight.Normal
            ),
            modifier = Modifier
                .clickable { onToggleChanged(true) }
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
    }
}


@Composable
fun MySkillList(skills: List<Skill?>, padding: PaddingValues, onSkillClick: (Skill) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(skills) { skill ->
            MySkillCard(skill, onSkillClick)
        }
    }
}

@Composable
fun MySkillCard(skill: Skill?, onSkillClick: (Skill) -> Unit) {
    if (skill != null) {
        Card(
            colors = CardDefaults.cardColors(containerColor = skill.color),
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
}

