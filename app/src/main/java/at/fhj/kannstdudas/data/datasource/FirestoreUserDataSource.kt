package at.fhj.kannstdudas.data.datasource

import android.net.Uri
import at.fhj.kannstdudas.domain.User
import at.fhj.kannstdudas.domain.datasource.UserDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.data.datasource
 * Created by Noah Dimmer on 17/06/2024
 */

class FirestoreUserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : UserDataSource {

    override suspend fun saveUser(user: User) {
        val userMap = mapOf(
            "email" to user.email,
            "username" to user.username,
            "profile_picture" to user.profilePicture,
            "uid" to user.uid
        )
        firestore.collection("users").document(user.uid).set(userMap).await()
    }

    override suspend fun getUser(uid: String): User? {
        val document = firestore.collection("users").document(uid).get().await()
        return document.toObject(User::class.java)
    }

    override suspend fun saveProfilePicture(uri: Uri, uid: String): Uri {
        val ref = firebaseStorage.reference.child("profile_pictures/$uid")
        ref.putFile(uri).await()
        return ref.downloadUrl.await()
    }

    override suspend fun getProfilePicture(url: String): ByteArray {
        val storageRef = firebaseStorage.getReferenceFromUrl(url)
        return storageRef.getBytes(Long.MAX_VALUE).await()
    }
}