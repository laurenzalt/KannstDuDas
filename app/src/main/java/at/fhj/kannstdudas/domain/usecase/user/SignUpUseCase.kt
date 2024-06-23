package at.fhj.kannstdudas.domain.usecase.user

import at.fhj.kannstdudas.data.datasource.FirestoreUserDataSource
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.model.User
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.domain.usecase
 * Created by Noah Dimmer on 13/06/2024
 */

class SignUpUseCase @Inject constructor(private val userRepository: UserRepository, private val userDataSource: FirestoreUserDataSource) {
    suspend operator fun invoke(user: User) {
        userRepository.signUp(user)
    }
}