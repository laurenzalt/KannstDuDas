package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.R
import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.model.User
import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.domain.usecase.skill.AddSubscribedSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.DeleteSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.EditSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.GetAllSkillsUseCase
import at.fhj.kannstdudas.domain.usecase.user.GetCurrentUserUseCase
import at.fhj.kannstdudas.domain.usecase.skill.GetSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.GetSkillsByUserUseCase
import at.fhj.kannstdudas.domain.usecase.skill.GetSubscribedSkillsUseCase
import at.fhj.kannstdudas.domain.usecase.skill.IsSubscribedToSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.SaveSkillUseCase
import at.fhj.kannstdudas.domain.usecase.skill.UnsubscribeSkillUseCase
import at.fhj.kannstdudas.infrastructure.util.ResourceProvider
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Noah Dimmer on 20/06/2024
 */

@Suppress("ImplicitThis")
@HiltViewModel
class SkillViewModel @Inject constructor(
    private val skillRepository: FirestoreSkillRepository,
    private val userRepository: UserRepository,
    private val getSkillUseCase: GetSkillUseCase,
    private val saveSkillUseCase: SaveSkillUseCase,
    private val deleteSkillUseCase: DeleteSkillUseCase,
    private val getSkillsByUserUseCase: GetSkillsByUserUseCase,
    private val isSubscribedToSkillUseCase: IsSubscribedToSkillUseCase,
    private val getAllSkillsUseCase: GetAllSkillsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val editSkillUseCase: EditSkillUseCase,
    private val addSubscribedSkillUseCase: AddSubscribedSkillUseCase,
    private val unsubscribeSkillUseCase: UnsubscribeSkillUseCase,
    private val getSubscribedSkillsUseCase: GetSubscribedSkillsUseCase,
    private val resourceProvider: ResourceProvider,
    ): ViewModel() {
    private val _skill = MutableStateFlow<Skill?>(null)
    val skill: StateFlow<Skill?> = _skill

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _userMessage = MutableStateFlow("")
    val userMessage: StateFlow<String> = _userMessage

    private val _isSubscribedToSkill = MutableStateFlow(false)
    val isSubscribedToSkill: StateFlow<Boolean> = _isSubscribedToSkill

    private val _isMySkill = MutableStateFlow(false)
    val isMySkill: StateFlow<Boolean> = _isMySkill

    private val _mySkills = MutableStateFlow<List<Skill?>>(emptyList())
    val mySkills: StateFlow<List<Skill?>> = _mySkills

    private val _subscribedSkills = MutableStateFlow<List<Skill?>>(emptyList())
    val subscribedSkills: StateFlow<List<Skill?>> = _subscribedSkills

    private val _skills = MutableStateFlow<List<Skill?>>(emptyList())
    val skills: StateFlow<List<Skill?>> = _skills

    private val _searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredSkills = _searchQuery.flatMapLatest { query ->
        _skills.map { allSkills ->
            if (query.isBlank()) {
                allSkills.filterNotNull()
            } else {
                allSkills.filterNotNull().filter { it.name.contains(query, ignoreCase = true) }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        getAllSkills()
    }

    fun saveSkill(skill: Skill) {
        viewModelScope.launch {
            try {
                saveSkillUseCase(skill)
                _userMessage.value = resourceProvider.getString(R.string.skill_successfully_added)
            } catch (e: FirebaseFirestoreException) {
                _userMessage.value = resourceProvider.getString(R.string.failed_to_add_skill)
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
                _userMessage.value = resourceProvider.getString(R.string.skill_successfully_deleted)
            } catch (e: FirebaseFirestoreException) {
                _userMessage.value = resourceProvider.getString(R.string.failed_to_delete_skill)
                println(e)
            }
        }
    }

    private fun getAllSkills() {
        viewModelScope.launch {
            try {
                _skills.value = getAllSkillsUseCase()
            } catch (e: FirebaseFirestoreException) {
                println(e)
                _skills.value = emptyList()
            }
        }
    }

    fun getSkillsByUser() {
        viewModelScope.launch {
            try {
                _user.value = userRepository.getCurrentUser()
                val currentUser = _user.value ?: return@launch
                _mySkills.value = getSkillsByUserUseCase(currentUser.uid)
            } catch (e: FirebaseFirestoreException) {
                println(e.message?.let {
                    resourceProvider.getString(R.string.error_fetching_skills,
                        it
                    )
                })
            }
        }
    }


    fun isSubscribedToSkill(skillId: String): Boolean {
        viewModelScope.launch {
            try {
                _user.value = getCurrentUserUseCase()
                _isSubscribedToSkill.value = isSubscribedToSkillUseCase(_user.value?.uid, skillId)
            } catch (e: FirebaseFirestoreException) {
                println(e)
                _isSubscribedToSkill.value = false
            }
        }
        return _isSubscribedToSkill.value
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

    fun addSubscribedSkill(skill: Skill) {
        viewModelScope.launch {
            try {
                val user = getCurrentUserUseCase()
                if (user != null) {
                    addSubscribedSkillUseCase(user.uid, skill)
                    checkIfSubscribed(skill.id)
                }
            } catch (e: FirebaseFirestoreException) {
                _userMessage.value =
                    resourceProvider.getString(R.string.failed_to_subscribe_to_skill)
                println(e)
            }
        }
    }

    fun unsubscribeSkill(skillId: String) {
        viewModelScope.launch {
            try {
                val user = getCurrentUserUseCase()
                if (user != null) {
                    unsubscribeSkillUseCase(user.uid, skillId)
                    checkIfSubscribed(skillId)
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    fun getSubscribedSkills() {
        viewModelScope.launch {
            try {
                _user.value = getCurrentUserUseCase()
                val currentUser = _user.value ?: return@launch
                _subscribedSkills.value = getSubscribedSkillsUseCase(currentUser.uid)
            } catch (e: FirebaseFirestoreException) {
                println(e)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun checkIfSubscribed(skillId: String) {
        viewModelScope.launch {
            try {
                val user = getCurrentUserUseCase()
                val isSubscribed = isSubscribedToSkillUseCase(user?.uid, skillId)
                _isSubscribedToSkill.value = isSubscribed
            } catch (e: Exception) {
                _isSubscribedToSkill.value = false
            }
        }
    }
}