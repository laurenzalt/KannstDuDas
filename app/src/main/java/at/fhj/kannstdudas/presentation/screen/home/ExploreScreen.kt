package at.fhj.kannstdudas.presentation.screen.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel
import at.fhj.kannstdudas.presentation.viewmodel.SkillsViewModel
import kotlinx.coroutines.launch

@Composable
fun ExploreScreen(navController: NavHostController, viewModel: SkillViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val skills = remember { mutableStateOf<List<Skill>>(listOf())}
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // val filteredSkills = viewModel.filteredSkills.collectAsState(initial = listOf()).value
    // val keyboardController = LocalSoftwareKeyboardController.current

    // Fetch skills when the screen is first compsoed

    LaunchedEffect(Unit) {
        scope.launch {
            skills.value = viewModel.getAllSkills()
        }
    }

    Scaffold(
        topBar = {
            SearchBar(searchQuery, onQueryChanged = {
                searchQuery = it
            }, onDone = { keyboardController?.hide() })
        }
    ) { padding ->
        SkillList(skills.value.filter { it.name.contains(searchQuery, ignoreCase = true) }, padding, onSkillClick = { skill ->
            navController.navigate("SkillDetail/${skill.id}")
        })
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit, onDone: () -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text("Search Skills") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    )
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