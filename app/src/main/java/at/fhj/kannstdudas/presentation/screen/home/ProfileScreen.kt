package at.fhj.kannstdudas.presentation.screen.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.navigation.Route
import at.fhj.kannstdudas.presentation.viewmodel.AuthViewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

/**
 * at.fhj.kannstdudas.presentation.screen
 * Created by Noah Dimmer on 12/06/2024
 */

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val isSignIn by authViewModel.isSignedIn.collectAsState()
    val user by authViewModel.user.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var picUrl by remember { mutableStateOf("") }
    var editMode by remember { mutableStateOf(false) }

    LaunchedEffect(isSignIn) {
        if (!isSignIn) {
            navController.navigate(Route.AuthNav) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        FirebaseFirestore.getInstance().collection("users")
            .document(currentUser)
            .get()
            .addOnSuccessListener {
                username = it.get("username").toString()
                email = it.get("email").toString()
                picUrl = it.get("profile_picture").toString()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage(Uri.parse(picUrl)) {
            updateProfilePicture(it) { newUrl ->
                picUrl = newUrl
                authViewModel.updateProfilePictureUrl(newUrl)
            }
        }
        EditableUserInfo(
            email = email,
            username = username,
            editMode = editMode,
            onEditModeChange = { editMode = it },
            onUsernameChange = {
                username = it
                updateUsernameInFirebase(it)
            }
        )
        SignOutButton(authViewModel)
    }
}

@Composable
fun EditableUserInfo(
    email: String,
    username: String,
    editMode: Boolean,
    onEditModeChange: (Boolean) -> Unit,
    onUsernameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(R.string.email)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text(text = stringResource(R.string.username)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,

                )
            },
            trailingIcon = {
                IconButton(onClick = { onEditModeChange(!editMode) }) {
                    Icon(
                        imageVector = if (editMode) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ProfileImage(imageUrl: Uri?, onImageChangeClick: (newUri: Uri) -> Unit = {}) {
    val color = MaterialTheme.colorScheme

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onImageChangeClick(it)
        }
    }

    Box(Modifier.height(140.dp)) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(3.dp, color.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageUrl,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(image = Icons.Default.AccountCircle),
                error = rememberVectorPainter(image = Icons.Default.AccountCircle),
                contentDescription = null,
            )
        }
        IconButton(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .size(35.dp)
                .padding(2.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd),
            colors = IconButtonDefaults.iconButtonColors(color.primary),
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                tint = color.onPrimary
            )
        }
    }
}

@Composable
fun SignOutButton(viewModel: AuthViewModel) {
    val color = MaterialTheme.colorScheme

    IconButton(
        onClick = {
            viewModel.logoutUser()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = IconButtonDefaults.iconButtonColors(color.primary),
        ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Logout,
                contentDescription = stringResource(id = R.string.profile_logout),
                tint = color.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.profile_logout),
                style = MaterialTheme.typography.labelMedium,
                color = color.onPrimary
            )
        }
    }
}

private fun updateProfilePicture(uri: Uri, onSuccess: (String) -> Unit) {
    val riversRef =
        FirebaseStorage.getInstance().getReference("profile_pictures/${FirebaseAuth.getInstance().currentUser?.uid.toString()}")
    val uploadTask = riversRef.putFile(uri)

    uploadTask.addOnFailureListener {
    }.addOnSuccessListener { taskSnapshot ->
        taskSnapshot.storage.downloadUrl.addOnSuccessListener { newUri ->
            FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .update("profile_picture", newUri.toString())
                .addOnSuccessListener {
                    onSuccess(newUri.toString())
                }
        }
    }
}

private fun updateUsernameInFirebase(newUsername: String) {
    val currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    FirebaseFirestore.getInstance().collection("users")
        .document(currentUser)
        .update("username", newUsername)
        .addOnSuccessListener {
        }
        .addOnFailureListener {
        }
}