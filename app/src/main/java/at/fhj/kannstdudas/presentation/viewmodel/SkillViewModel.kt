package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.User
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.domain.usecase.skill.DeleteSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.EditSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.GetAllSkillsUseCase
import at.fhj.kannstdudas.domain.usecase.user.GetCurrentUserUseCase
import at.fhj.kannstdudas.domain.usecase.skill.GetSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.GetSkillsByUserUseCase
import at.fhj.kannstdudas.domain.usecase.skill.IsSubscribedToSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.SaveSkillUseCase
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Noah Dimmer on 20/06/2024
 */

@Suppress("ImplicitThis")
@HiltViewModel
class SkillViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val getSkillUseCase: GetSkillUseCase,
    private val saveSkillUseCase: SaveSkillUseCase,
    private val deleteSkillUseCase: DeleteSkillUseCase,
    private val getSkillsByUserUseCase: GetSkillsByUserUseCase,
    private val isSubscribedToSkillUseCase: IsSubscribedToSkillUseCase,
    private val getAllSkillsUseCase: GetAllSkillsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val editSkillUseCase: EditSkillUseCase
): ViewModel() {
    private val _skill = MutableStateFlow<Skill?>(null)
    val skill: StateFlow<Skill?> = _skill

    private val _skills = MutableStateFlow<List<Skill?>>(emptyList())
    val skills: StateFlow<List<Skill?>> = _skills

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _userMessage = MutableStateFlow("")
    val userMessage: StateFlow<String> = _userMessage

    private val _isSubscribedToSkill = MutableStateFlow(false)
    val isSubscribedToSkill: StateFlow<Boolean> = _isSubscribedToSkill

    private val _isMySkill = MutableStateFlow(false)
    val isMySkill: StateFlow<Boolean> = _isMySkill


    init {
        getAllSkills()
    }

    fun saveSkill(skill: Skill) {
        viewModelScope.launch {
            try {
                saveSkillUseCase(skill)
                _userMessage.value = "Skill successfully added"
            } catch (e: FirebaseFirestoreException) {
                _userMessage.value = "Failed to add skill"
                println(e)
            }
        }
    }

    fun getSkill(skillId: String): Skill? {
        viewModelScope.launch {
            try {
                _skill.value = getSkillUseCase(skillId)
            } catch (e: FirebaseFirestoreException) {
                println(e)
                _skill.value = null
            }
        }
        return _skill.value
    }

    fun deleteSkill(skillId: String) {
        viewModelScope.launch {
            try {
                deleteSkillUseCase(skillId)
                // skillRepository.deleteSkill(skillId)
                _userMessage.value = "Skill successfully deleted"
            } catch (e: FirebaseFirestoreException) {
                _userMessage.value = "Failed to delete skill"
                println(e)
            }
        }
    }

    private fun getAllSkills() {
        viewModelScope.launch {
            try {
                _skills.value = getAllSkillsUseCase()
                // skillRepository.getAllSkills()
            } catch (e: FirebaseFirestoreException) {
                println(e)
                _skills.value = emptyList()
            }
        }
    }

    fun getSkillsByUser(): List<Skill?> {
        viewModelScope.launch {
            try {
                _user.value = userRepository.getCurrentUser()
            } catch (e: FirebaseFirestoreException) {
                println(e)
                _user.value = null
            }

            if (_user.value != null) {
                try {
                    _skills.value = getSkillsByUserUseCase(_user.value?.uid.toString())
                } catch (e: FirebaseFirestoreException) {
                    println("Error fetching skills: ${e.message}")
                }
            }
        }
        return _skills.value
    }

    fun isMySkill(skillId: String) {
        viewModelScope.launch {
            try {
                _skill.value = getSkillUseCase(skillId)
                _user.value = getCurrentUserUseCase()
                _isMySkill.value = _skill.value?.userId  == _user.value?.uid
            } catch (e: FirebaseFirestoreException) {
                println(e)
            }
        }
//        return _isMySkill.value
    }

    fun clearDeletionMessage() {
        _userMessage.value = ""
    }

    fun isSubscribedToSkill(skillId: String) {
        viewModelScope.launch {
            try {
                _user.value = getCurrentUserUseCase()
                _isSubscribedToSkill.value = isSubscribedToSkillUseCase(_user.value?.uid, skillId)
            } catch (e: FirebaseFirestoreException) {
                println(e)
                _isSubscribedToSkill.value = false
            }
        }
//        return _isSubscribedToSkill.value
    }

    fun editSkill(skill: Skill) {
        viewModelScope.launch {
            try {
                editSkillUseCase(skill)
            } catch (e: FirebaseFirestoreException) {
                println(e)
            }
        }
    }
}