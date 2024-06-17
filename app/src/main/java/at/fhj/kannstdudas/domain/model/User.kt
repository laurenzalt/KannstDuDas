package at.fhj.kannstdudas.domain

import at.fhj.kannstdudas.domain.model.Skill

/**
 * at.fhj.kannstdudas.domain.model
 * Created by Noah Dimmer on 13/06/2024
 */

// Nullables are for testing
data class User(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val profilePicture: String = "",
    val uid: String = "",

    // Maybe have this as separate userskills class?
    val mySkills: List<Skill> = listOf(),
    val subscribedSkills: List<Skill> = listOf()
) {

}