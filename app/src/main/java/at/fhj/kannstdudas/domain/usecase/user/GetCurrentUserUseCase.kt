package at.fhj.kannstdudas.domain.usecase.user

import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.model.User
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.domain.usecase
 * Created by Noah Dimmer on 15/06/2024
 */

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? {
        return userRepository.getCurrentUser()
    }
}