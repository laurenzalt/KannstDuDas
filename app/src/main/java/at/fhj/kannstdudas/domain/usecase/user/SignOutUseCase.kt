package at.fhj.kannstdudas.domain.usecase.user

import at.fhj.kannstdudas.data.repository.UserRepository
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.domain.usecase
 * Created by Noah Dimmer on 15/06/2024
 */

class SignOutUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() {
        userRepository.signOut()
    }
}