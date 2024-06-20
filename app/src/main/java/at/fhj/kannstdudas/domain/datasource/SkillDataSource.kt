package at.fhj.kannstdudas.domain.datasource

import at.fhj.kannstdudas.domain.model.Skill

/**
 * at.fhj.kannstdudas.domain.datasource
 * Created by Noah Dimmer on 20/06/2024
 */
interface SkillDataSource {
    suspend fun saveSkill(skill: Skill)
    suspend fun getSkill(skillId: String): Skill?
    suspend fun deleteSkill(skillId: String)
    suspend fun getAllSkills(): List<Skill>
    suspend fun getSkillsByUser(userId: String): List<Skill>
    suspend fun isSubscribedToSkill(userId: String, skillId: String): Boolean
}