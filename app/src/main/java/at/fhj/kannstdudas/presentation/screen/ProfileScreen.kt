package at.fhj.kannstdudas.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun ProfileScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting()
        ProfilePicture()
        UserInfo()
        Logout()
    }
}

@Composable
fun Greeting(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser = viewModel.fetchCurrentUser()
    Text(
        text = "Hello, $currentUser",
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun ProfilePicture() {

    //TODO: Firestore user image?
    val imageUri = rememberSaveable { mutableStateOf("") }

    val painter = painterResource(id = R.drawable.test_profile_icon)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .size(150.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
    }
}

@Composable
fun UserInfo() {
    val padding = Modifier.padding(8.dp)

    Card(
        modifier = padding
    ) {
        Column(modifier = padding) {
            StyledText(label = "Vorname", value = "Placeholder")
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(4.dp))
            StyledText(label = "Nachname", value = "Namington")
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(4.dp))
            StyledText(label = "Email", value = "nofirestore@test.com")
        }
    }
}

@Composable
fun StyledText(label: String, value: String) {
    Text(
        text = buildAnnotatedString {
            append("$label: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
            append(value)
            pop()
        },
        style = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun Logout(
    viewModel: AuthViewModel = hiltViewModel()
) {
    Button(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        onClick = { viewModel.logoutUser() },
    ) {
        Text(stringResource(R.string.profile_logout))
    }
}