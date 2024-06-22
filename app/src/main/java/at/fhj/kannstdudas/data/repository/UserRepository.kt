package at.fhj.kannstdudas.data.repository

import android.net.Uri
import at.fhj.kannstdudas.domain.User
import at.fhj.kannstdudas.domain.datasource.UserDataSource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.data.repository
 * Created by Noah Dimmer on 13/06/2024
 */

class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDataSource: UserDataSource
) {
    suspend fun signIn(user: User) {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
    }

    suspend fun signUp(user: User) {
        val result = firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
        val firebaseUser = result.user ?: throw Exception("Failed to create user")
        val newUser = user.copy(uid = firebaseUser.uid)
        userDataSource.saveUser(newUser)
    }

    suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun getCurrentUser(): User? {
        val uid = firebaseAuth.currentUser?.uid
        return uid?.let { userDataSource.getUser(it) }
    }

    suspend fun uploadProfilePicture(uri: Uri): Uri {
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
        return userDataSource.saveProfilePicture(uri, uid)
    }
}