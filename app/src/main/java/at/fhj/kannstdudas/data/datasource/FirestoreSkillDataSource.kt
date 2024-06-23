package at.fhj.kannstdudas.data.datasource

import at.fhj.kannstdudas.domain.datasource.SkillDataSource
import at.fhj.kannstdudas.domain.model.Skill
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.data.datasource
 * Created by Noah Dimmer on 20/06/2024
 */

class FirestoreSkillDataSource @Inject constructor(
    firestore: FirebaseFirestore
) : SkillDataSource {

    private val skillCollection = firestore.collection("skills")

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
        //val doc = subscriptionsCollection.document(userId).get().await()
        //val subscribedSkills = doc.get("skillIds") as? List<String>
        //return subscribedSkills?.contains(skillId) ?: false
        return true
    }

    override suspend fun editSkill(skill: Skill) {
        try {
            skillCollection.document(skill.id).set(skill).await()
        } catch (e: FirebaseFirestoreException) {
            throw e
        }
    }

}