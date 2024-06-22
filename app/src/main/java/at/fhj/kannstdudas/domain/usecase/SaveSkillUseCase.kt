package at.fhj.kannstdudas.domain.usecase

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.domain.model.Skill
import javax.inject.Inject

class SaveSkillUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(skill: Skill) {
        skillRepository.saveSkill(skill)
    }
}