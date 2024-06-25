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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.domain.model.Category
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import at.fhj.kannstdudas.presentation.viewmodel.SkillViewModel
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 13/06/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSkillScreen(navController: NavHostController, viewModel: SkillViewModel = hiltViewModel(), userViewModel: AuthViewModel = hiltViewModel(),) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf(Category.Programming) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val user by userViewModel.user.collectAsState()
    val context = LocalContext.current

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
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

            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {

                        var skill = user?.let { Skill(UUID.randomUUID().toString(), title, description, category, it.uid) }
                        if (skill != null) {
                            viewModel.saveSkill(skill)
                        }

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.skill_added_successfully),
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
                Text(stringResource(R.string.create))
            }
        }
    }
}


@Composable
fun TitleInput(title: String, onTitleChange: (String) -> Unit) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        label = { Text(stringResource(R.string.title)) },
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
        onExpandedChange = setIsExpanded,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = stringResource(id = category.label),
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onCategoryChange(category, false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Category.values().forEach { categoryItem ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = categoryItem.label)) },
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
        label = { Text(stringResource(R.string.description)) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 20,
        textStyle = LocalTextStyle.current.copy(lineHeight = 20.sp)
    )
}
