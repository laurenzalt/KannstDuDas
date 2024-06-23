package at.fhj.kannstdudas.presentation.screen.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.domain.model.User
import at.fhj.kannstdudas.navigation.Route
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun ProfileScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val isSignIn by viewModel.isSignedIn.collectAsState()
    val user by viewModel.user.collectAsState()

    LaunchedEffect(isSignIn) {
        if (!isSignIn) {
            navController.navigate(Route.AuthNav) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(user)
        ProfilePicture(viewModel, user)
        UserInfo(user)
        SignOut(viewModel)
    }
}

@Composable
fun Greeting(user: User?) {
    Text(
        text = "Hello, ${user?.username ?: "Guest"}",
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        overflow = TextOverflow.Ellipsis
    )
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfilePicture(viewModel: AuthViewModel, user: User?) {
    val permissionState = rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    val painter = rememberAsyncImagePainter(
        model = user?.profile_picture,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.test_profile_icon),

    )

    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var hasPermission by rememberSaveable { mutableStateOf(permissionState.status.isGranted) }

    LaunchedEffect(selectedImageUri, hasPermission) {
        if (hasPermission && selectedImageUri != null) {
            viewModel.uploadProfilePicture(selectedImageUri!!)
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
                if (permissionState.status.isGranted) {
                    hasPermission = true
                } else {
                    permissionState.launchPermissionRequest()
                }
            }
        }
    )

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
        Button(
            onClick = {
                if (permissionState.status.isGranted) {
                    pickImageLauncher.launch("image/*")
                } else {
                    permissionState.launchPermissionRequest()
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }
    }
}

@Composable
fun UserInfo(user: User?) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            StyledText(label = "Email", value = user?.email ?: "nofirestore@test.com")
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
fun SignOut(viewModel: AuthViewModel) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {
            viewModel.logoutUser()
        },
    ) {
        Text(stringResource(R.string.profile_logout))
    }
}