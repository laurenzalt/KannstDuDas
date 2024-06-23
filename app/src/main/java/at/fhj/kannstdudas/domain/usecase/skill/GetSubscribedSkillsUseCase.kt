package at.fhj.kannstdudas.domain.usecase.skill

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.domain.model.Skill
import javax.inject.Inject

class GetSubscribedSkillsUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(userid: String): List<Skill?> {
        return skillRepository.getSubscribedSkills(userid)
    }
}