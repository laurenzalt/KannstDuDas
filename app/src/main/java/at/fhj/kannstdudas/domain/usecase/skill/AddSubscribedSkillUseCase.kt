package at.fhj.kannstdudas.domain.usecase.skill

import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.domain.model.Skill
import javax.inject.Inject

class AddSubscribedSkillUseCase @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
) {
    suspend operator fun invoke(userid: String, skill: Skill) {
        skillRepository.addSubscribedSkill(userid, skill)
    }
}