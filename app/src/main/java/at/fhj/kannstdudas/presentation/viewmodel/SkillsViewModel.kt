package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import at.fhj.kannstdudas.data.repository.LocalSkillRepository
import at.fhj.kannstdudas.domain.model.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Laurenz Altendorfer on 16/06/2024
 */

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val skillsRepository: LocalSkillRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredSkills = _searchQuery.flatMapLatest { query ->
        skillsRepository.skills.map { skills ->
            if (query.isEmpty()) skills else skills.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    val skills = skillsRepository.skills
    val mySkills = skillsRepository.mySkills
    val subscribedSkills = skillsRepository.subscribedSkills

    fun addSkill(skill: Skill) {
        skillsRepository.addSkill(skill)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getSkillById(id: String): Skill? {
        return skillsRepository.getSkillById(id)
    }

    fun addSubscribedSkill(skill: Skill) {
        viewModelScope.launch {
            skillsRepository.addSubscribedSkill(skill)
        }
    }

    fun addMySkills(skill: Skill) {
        viewModelScope.launch {
            skillsRepository.addMySkills(skill)
        }
    }

    fun isMySkill(skillId: String): Boolean {
        return mySkills.value.any { it.id == skillId }
    }

    fun deleteSkill(skill: Skill) {
        viewModelScope.launch {
            skillsRepository.deleteSkill(skill.id)
        }
    }

    fun editSkill(skill: Skill) {
        viewModelScope.launch {
            skillsRepository.updateSkill(skill)
        }
    }

    fun isSubscribedSkill(skillId: String): Boolean {
        return subscribedSkills.value.any { it.id == skillId }
    }

    fun unsubscribeSkill(skill: Skill) {
        viewModelScope.launch {
            skillsRepository.removeSubscribedSkill(skill.id)
        }
    }

}