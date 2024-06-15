package at.fhj.kannstdudas.presentation.shared.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.fhj.kannstdudas.R

/**
 * at.fhj.kannstdudas.presentation.shared.authentication
 * Created by Noah Dimmer on 15/06/2024
 */

@Composable
fun SignUpButton(
    onClick: () -> Unit,
    isEnabled: Boolean,
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        enabled = isEnabled
    ) {
        Text(text = stringResource(id = R.string.sign_up_button))
    }
}
