package at.fhj.kannstdudas.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel

@Composable
fun ExploreScreen(navController: NavHostController, viewModel: SkillViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val filteredSkills = viewModel.filteredSkills.collectAsState(initial = listOf()).value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Rtl),
                    bottom = 0.dp
                )
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChanged = {
                    searchQuery = it
                    viewModel.setSearchQuery(it)
                },
                onDone = { keyboardController?.hide() }
            )
            SkillList(
                skills = filteredSkills,
                padding = PaddingValues(horizontal = 16.dp),
                onSkillClick = { skill ->
                    navController.navigate("SkillDetail/${skill.id}")
                }
            )
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit, onDone: () -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text(stringResource(R.string.search_skills)) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .padding(top = 0.dp),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    )
}

@Composable
fun SkillList(skills: List<Skill?>, padding: PaddingValues, onSkillClick: (Skill) -> Unit) {
    LazyColumn(
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(skills) { skill ->
            skill?.let { safeSkill ->
                SkillCard(safeSkill, onSkillClick)
            }
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