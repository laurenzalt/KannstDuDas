package at.fhj.kannstdudas.domain.usecase.skill

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import javax.inject.Inject

class UnsubscribeSkillUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(userid: String, skillId: String) {
        skillRepository.unsubscribeSkill(userid, skillId)
    }
}