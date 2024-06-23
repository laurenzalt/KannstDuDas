package at.fhj.kannstdudas.data.datasource

import at.fhj.kannstdudas.domain.User
import at.fhj.kannstdudas.domain.datasource.SkillDataSource
import at.fhj.kannstdudas.domain.model.Skill
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.data.datasource
 * Created by Noah Dimmer on 20/06/2024
 */

class FirestoreSkillDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : SkillDataSource {

    private val skillCollection = firestore.collection("skills")
    private val usersCollection = firestore.collection("users")

    override suspend fun saveSkill(skill: Skill) {
        skillCollection.document(skill.id).set(skill).await()
    }

    override suspend fun getSkill(skillId: String): Skill? {
        val document = skillCollection.document(skillId).get().await()
        return document.toObject(Skill::class.java)
    }

    override suspend fun deleteSkill(skillId: String) {
        skillCollection.document(skillId).delete().await()
    }

    override suspend fun getAllSkills(): List<Skill> {
        val result = skillCollection.get().await()
        return result.toObjects(Skill::class.java)    }

    override suspend fun getSkillsByUser(userId: String): List<Skill> {
        val result = skillCollection.whereEqualTo("userId", userId).get().await()
        return result.toObjects(Skill::class.java)    }

    override suspend fun isSubscribedToSkill(userId: String, skillId: String): Boolean {
        val userDoc = usersCollection.document(userId).get().await()
        val subscribedSkills = userDoc.toObject(User::class.java)?.subscribedSkills ?: listOf()
        return subscribedSkills.any { it.id == skillId }
    }

    override suspend fun editSkill(skill: Skill) {
        try {
            skillCollection.document(skill.id).set(skill).await()
        } catch (e: FirebaseFirestoreException) {
            throw e
        }
    }

    override suspend fun addSubscribedSkill(userId: String, skill: Skill) {
        firestore.runTransaction { transaction ->
            val userRef = usersCollection.document(userId)
            val userSnapshot = transaction.get(userRef)
            val user = userSnapshot.toObject<User>() ?: return@runTransaction

            // Add the skill if it's not already subscribed
            if (user.subscribedSkills.none { it.id == skill.id }) {
                val updatedSkills = user.subscribedSkills.toMutableList()
                updatedSkills.add(skill)
                transaction.update(userRef, "subscribedSkills", updatedSkills)
            }
        }.await()
    }

    override suspend fun unsubscribeSkill(userId: String, skillId: String) {
        // Use Firestore transaction to ensure atomicity
        firestore.runTransaction { transaction ->
            val userRef = usersCollection.document(userId)
            val userSnapshot = transaction.get(userRef)
            val user = userSnapshot.toObject<User>() ?: return@runTransaction

            // Remove the skill if it is subscribed
            val updatedSkills = user.subscribedSkills.filterNot { it.id == skillId }.toList()
            transaction.update(userRef, "subscribedSkills", updatedSkills)
        }.await()
    }

    override suspend fun getSubscribedSkills(userId: String): List<Skill> {
        val userDoc = usersCollection.document(userId).get().await()
        return userDoc.toObject<User>()?.subscribedSkills ?: emptyList()
    }

}