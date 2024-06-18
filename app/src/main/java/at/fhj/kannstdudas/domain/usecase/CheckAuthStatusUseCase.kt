package at.fhj.kannstdudas.domain.usecase

import at.fhj.kannstdudas.data.repository.UserRepository
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.domain.usecase
 * Created by Noah Dimmer on 18/06/2024
 */

class CheckAuthStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun invoke(): Boolean {
        return userRepository.isUserAuthenticated()
    }
}