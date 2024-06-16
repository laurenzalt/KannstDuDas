package at.fhj.kannstdudas.domain.model

import at.fhj.kannstdudas.domain.model.Skill

/**
 * at.fhj.kannstdudas.domain.model
 * Created by Noah Dimmer on 13/06/2024
 */

data class User(
    val email: String,
    val username: String,
    val password: String,
    val mySkills: List<Skill> = listOf(),
    val subscribedSkills: List<Skill> = listOf()
)