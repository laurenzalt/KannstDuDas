package at.fhj.kannstdudas.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.User
import at.fhj.kannstdudas.domain.model.Skill
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Noah Dimmer on 20/06/2024
 */

@HiltViewModel
class SkillViewModel @Inject constructor(
    private val skillRepository: FirestoreSkillRepository,
    private val userRepository: UserRepository
): ViewModel() {
    var skill by mutableStateOf<Skill?>(null)
        private set


    init {
    }

    fun saveSkill(skill: Skill) {
        viewModelScope.launch {
            try {
                skillRepository.saveSkill(skill)
            } catch (e: FirebaseFirestoreException) {
                println(e)
            }
        }
    }

    suspend fun getSkill(skillId: String): Skill? {
        return try {
            skillRepository.getSkill(skillId)
        } catch (e: FirebaseFirestoreException) {
            println(e)
            null
        }
    }

    fun deleteSkill(skillId: String) {
        viewModelScope.launch {
            try {
                skillRepository.deleteSkill(skillId)
            } catch (e: FirebaseFirestoreException) {
                println(e)
            }
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

    suspend fun isMySkill(skillId: String): Boolean {
        val skill = try {
            skillRepository.getSkill(skillId)
        } catch (e: FirebaseFirestoreException) {
            println(e)
            return false
        }
        val user = try {
            userRepository.getCurrentUser()
        } catch (e: FirebaseFirestoreException) {
            println(e)
            return false
        }
        return skill?.userId == user?.uid
    }
}