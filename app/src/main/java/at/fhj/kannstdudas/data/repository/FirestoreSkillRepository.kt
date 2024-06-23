package at.fhj.kannstdudas.data.repository

import at.fhj.kannstdudas.domain.datasource.SkillDataSource
import at.fhj.kannstdudas.domain.model.Skill
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.data.repository
 * Created by Noah Dimmer on 20/06/2024
 */

class FirestoreSkillRepository @Inject constructor(
    private val skillDataSource: SkillDataSource
) {
    private val _skills = MutableStateFlow<List<Skill>>(emptyList())
    val skills: StateFlow<List<Skill>> = _skills
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

    suspend fun addSubscribedSkill(userId: String, skill: Skill) {
        skillDataSource.addSubscribedSkill(userId, skill)
    }

    suspend fun unsubscribeSkill(userId: String, skillId: String) {
        skillDataSource.unsubscribeSkill(userId, skillId)
    }

    suspend fun getSubscribedSkills(userId: String): List<Skill> {
        return skillDataSource.getSubscribedSkills(userId)
    }
}