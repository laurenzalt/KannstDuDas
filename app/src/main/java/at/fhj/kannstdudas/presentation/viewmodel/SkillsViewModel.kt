package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.data.repository.SkillsRepository
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.model.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val skills = skillsRepository.skills
    val mySkills = skillsRepository.mySkills
    val subscribedSkills = skillsRepository.subscribedSkills

    fun addSkill(skill: Skill) {
        skillsRepository.addSkill(skill)
    }
}