package at.fhj.kannstdudas.domain.datasource

import at.fhj.kannstdudas.domain.User

/**
 * at.fhj.kannstdudas.domain.model.User
 * Created by Noah Dimmer on 17/06/2024
 */

interface UserDataSource {
    suspend fun saveUser(user: User)
    suspend fun getUser(uid: String): User?
}