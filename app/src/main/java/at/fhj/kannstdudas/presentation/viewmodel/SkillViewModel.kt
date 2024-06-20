package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.domain.model.Skill
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Noah Dimmer on 20/06/2024
 */

class SkillViewModel @Inject constructor(
    private val skillRepository: FirestoreSkillRepository
): ViewModel() {

    fun saveSkill(skill: Skill) {
        viewModelScope.launch {
            try {
                skillRepository.saveSkill(skill)
            } catch (e: FirebaseFirestoreException) {
                println(e)
            }

        }
    }

    suspend fun getSkill(skillId: String) {
         try {
             skillRepository.getSkill(skillId)
         } catch (e: FirebaseFirestoreException) {
             println(e)
         }

    }

    suspend fun deleteSkill(skillId: String) {
        try {
            skillRepository.deleteSkill(skillId)
        } catch (e: FirebaseFirestoreException) {
            println(e)
        }
    }

    suspend fun getAllSkills(): List<Skill> {
        return try {
            skillRepository.getAllSkills()
        } catch (e: FirebaseFirestoreException) {
            println(e)
            emptyList()
        }
    }

    suspend fun getSkillsByUser(userId: String): List<Skill> {
        return try {
            skillRepository.getSkillsByUser(userId)
        } catch (e: FirebaseFirestoreException) {
            println(e)
            emptyList()
        }
    }
}