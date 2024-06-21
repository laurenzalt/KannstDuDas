package at.fhj.kannstdudas.presentation.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.domain.model.Category
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel
import at.fhj.kannstdudas.presentation.viewmodel.SkillsViewModel
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 13/06/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSkillScreen(navController: NavHostController, viewModel: SkillViewModel = hiltViewModel()) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf(Category.Programming) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    if (it.length <= 28) title = it
                },
                label = { Text("Title") },
                singleLine = true,
                trailingIcon = {
                    Text("${title.length}/28", style = MaterialTheme.typography.bodySmall)
                },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = category.name,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Programming") },
                        onClick = {
                            category = Category.Programming
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Sports") },
                        onClick = {
                            category = Category.Sports
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Finance") },
                        onClick = {
                            category = Category.Finance
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Art") },
                        onClick = {
                            category = Category.Art
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Music") },
                        onClick = {
                            category = Category.Music
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Languages") },
                        onClick = {
                            category = Category.Languages
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Health") },
                        onClick = {
                            category = Category.Health
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Fitness") },
                        onClick = {
                            category = Category.Fitness
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Other") },
                        onClick = {
                            category = Category.Other
                            isExpanded = false
                        }
                    )
                }
            }
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 20,
                textStyle = LocalTextStyle.current.copy(lineHeight = 20.sp)
            )
            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        val newSkill = Skill(UUID.randomUUID().toString(), title, description, category)
                        coroutineScope.launch {
                            viewModel.saveSkill(newSkill)
                            snackbarHostState.showSnackbar(
                                message = "Skill added successfully!",
                                duration = SnackbarDuration.Short
                            )
                        }
                        title = ""
                        category = Category.Programming
                        description = ""
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Create")
            }
        }
    }
}