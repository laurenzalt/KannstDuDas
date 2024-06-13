package at.fhj.kannstdudas.data.repository

import at.fhj.kannstdudas.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.data.repository
 * Created by Noah Dimmer on 13/06/2024
 */

class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {    suspend fun signIn(user: User) {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
    }

    suspend fun signUp(user: User) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
    }

    suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser() = firebaseAuth.currentUser
}