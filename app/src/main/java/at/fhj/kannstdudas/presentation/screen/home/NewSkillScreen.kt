package at.fhj.kannstdudas.presentation.screen

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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.domain.model.Category
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.navigation.Screen
import at.fhj.kannstdudas.presentation.viewmodel.SkillsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 13/06/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSkillScreen(navController: NavHostController, viewModel: SkillsViewModel = hiltViewModel()) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf(Category.Programming) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TitleInput(title) { newTitle ->
                if (newTitle.length <= 28) title = newTitle
            }
            CategorySelector(isExpanded, category, { newCategory, newIsExpanded ->
                category = newCategory
                isExpanded = newIsExpanded
            }, { newIsExpanded ->
                isExpanded = newIsExpanded
            })
            DescriptionInput(description) { newDescription -> description = newDescription }
            // CreateButton(title, description, category, viewModel, snackbarHostState, coroutineScope, focusManager, keyboardController)
            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        var skill = Skill(UUID.randomUUID().toString(), title, description, category)
                        viewModel.addSkill(skill)
                        viewModel.addMySkills(skill)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Skill added successfully!",
                                duration = SnackbarDuration.Short
                            )
                        }
                        title = ""
                        description = ""
                        category = Category.Programming
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


@Composable
fun TitleInput(title: String, onTitleChange: (String) -> Unit) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        label = { Text("Title") },
        singleLine = true,
        trailingIcon = { Text("${title.length}/28", style = MaterialTheme.typography.bodySmall) },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelector(isExpanded: Boolean, category: Category, onCategoryChange: (Category, Boolean) -> Unit, setIsExpanded: (Boolean) -> Unit) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = setIsExpanded,  // Use the passed setter function
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = category.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onCategoryChange(category, false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Category.values().forEach { categoryItem ->
                DropdownMenuItem(
                    text = { Text(text = categoryItem.name) },
                    onClick = { onCategoryChange(categoryItem, false) }
                )
            }
        }
    }
}

@Composable
fun DescriptionInput(description: String, onDescriptionChange: (String) -> Unit) {
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        label = { Text("Description") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 20,
        textStyle = LocalTextStyle.current.copy(lineHeight = 20.sp)
    )
}
