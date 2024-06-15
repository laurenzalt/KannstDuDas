package at.fhj.kannstdudas.presentation.shared.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import at.fhj.kannstdudas.R

/**
 * at.fhj.kannstdudas.presentation.shared
 * Created by Noah Dimmer on 15/06/2024
 */

@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
) {
    OutlinedTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        label = { Text(text = stringResource(id = R.string.email_hint)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { keyboardController?.hide() }
        )
    )
}