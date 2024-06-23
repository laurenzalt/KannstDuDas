package at.fhj.kannstdudas.domain

import android.net.Uri
import at.fhj.kannstdudas.domain.model.Skill

/**
 * at.fhj.kannstdudas.domain.model
 * Created by Noah Dimmer on 13/06/2024
 */

data class User(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val profile_picture: String = "",
    val uid: String = "",
    val mySkills: List<Skill> = listOf(),
    val subscribedSkills: List<Skill> = listOf()
) {
    val profilePictureUri: Uri
        get() = if (profile_picture.isNotEmpty()) Uri.parse(profile_picture) else Uri.EMPTY
}