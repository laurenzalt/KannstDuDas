package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.data.repository.SkillsRepository
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.model.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Laurenz Altendorfer on 16/06/2024
 */

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val skillsRepository: SkillsRepository
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
}