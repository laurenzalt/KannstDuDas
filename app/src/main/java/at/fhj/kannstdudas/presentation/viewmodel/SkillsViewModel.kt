package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.data.repository.SkillsRepository
import at.fhj.kannstdudas.domain.model.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val skillsRepository: SkillsRepository
) : ViewModel() {
    val skills = skillsRepository.skills

    fun addSkill(skill: Skill) {
        skillsRepository.addSkill(skill)
    }
}