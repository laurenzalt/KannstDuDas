package at.fhj.kannstdudas.domain.usecase

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import javax.inject.Inject

class IsSubscribedToSkillUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(userId: String?, skillId: String): Boolean {
        return skillRepository.isSubscribedToSkill(userId.toString(), skillId)
    }
}