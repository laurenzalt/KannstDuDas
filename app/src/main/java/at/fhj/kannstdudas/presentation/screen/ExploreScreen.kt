package at.fhj.kannstdudas.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.model.Category
import at.fhj.kannstdudas.model.Skill
import at.fhj.kannstdudas.model.SkillsViewModel
import at.fhj.kannstdudas.navigation.Screen

@Composable
fun ExploreScreen(navController: NavHostController, viewModel: SkillsViewModel = hiltViewModel()) {
    
    Scaffold() { padding ->
        SkillList(viewModel._skills, padding, onSkillClick = { skill ->
            // mit id übergeben um zum jeweiligen Skill zu kommen
            navController.navigate(Screen.SkillDetail)
        })
    }
}

@Composable
fun SkillList(skills: List<Skill>, padding: PaddingValues, onSkillClick: (Skill) -> Unit) {
    LazyColumn(
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(skills) { skill ->
            SkillCard(skill, onSkillClick)
        }
    }
}

@Composable
fun SkillCard(skill: Skill, onSkillClick: (Skill) -> Unit) {
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