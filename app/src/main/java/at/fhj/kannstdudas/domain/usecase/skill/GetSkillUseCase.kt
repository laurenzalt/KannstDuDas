package at.fhj.kannstdudas.domain.usecase.skill

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.domain.model.Skill
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetSkillUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(skillId: String): Skill? {
        return skillRepository.getSkill(skillId)
    }
}