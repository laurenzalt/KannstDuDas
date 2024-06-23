package at.fhj.kannstdudas.domain.usecase.skill

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import javax.inject.Inject

class DeleteSkillUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(skillId: String) {
        skillRepository.deleteSkill(skillId)
    }
}