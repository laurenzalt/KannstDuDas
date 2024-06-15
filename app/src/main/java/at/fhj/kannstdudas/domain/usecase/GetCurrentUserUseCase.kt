package at.fhj.kannstdudas.domain.usecase

import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.model.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.domain.usecase
 * Created by Noah Dimmer on 15/06/2024
 */

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): FirebaseUser? {
        return userRepository.getCurrentUser()
    }
}