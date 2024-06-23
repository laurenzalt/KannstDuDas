package at.fhj.kannstdudas.data.repository

import at.fhj.kannstdudas.domain.datasource.SkillDataSource
import at.fhj.kannstdudas.domain.model.Skill
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.data.repository
 * Created by Noah Dimmer on 20/06/2024
 */
class FirestoreSkillRepository @Inject constructor(
    private val skillDataSource: SkillDataSource
) {
    suspend fun saveSkill(skill: Skill) {
        skillDataSource.saveSkill(skill)
    }

    suspend fun getSkill(skillId: String): Skill? {
        return skillDataSource.getSkill(skillId)
    }

    suspend fun deleteSkill(skillId: String) {
        skillDataSource.deleteSkill(skillId)
    }

    suspend fun getAllSkills(): List<Skill> {
        return skillDataSource.getAllSkills()
    }

    suspend fun getSkillsByUser(userId: String): List<Skill> {
        return skillDataSource.getSkillsByUser(userId)
    }

    suspend fun isSubscribedToSkill(userId: String, skillId: String): Boolean {
        return skillDataSource.isSubscribedToSkill(userId, skillId)
    }

    suspend fun editSkill(skill: Skill) {
        skillDataSource.editSkill(skill)
    }
}