package at.fhj.kannstdudas.domain.usecase.skill

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.domain.model.Skill
import javax.inject.Inject

class GetAllSkillsUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(): List<Skill> {
        return skillRepository.getAllSkills()
    }
}