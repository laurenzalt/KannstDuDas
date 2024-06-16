package at.fhj.kannstdudas.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.model.Skill
import at.fhj.kannstdudas.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class Category(val color: Color) {
    Programming(Color(0xFFAED581)),
    Sports(Color(0xFFFFD54F)),
    Finance(Color(0xFF4FC3F7)),
    Art(Color(0xFFF06292)),
    Music(Color(0xFF2D2C7D)),
    Languages(Color(0xFFBA68C8)),
    Health(Color(0xFFFF8A65)),
    Fitness(Color(0xFF4DB6AC)),
    Other(Color(0xFF90A4AE))
}


data class Skill(
    val name: String,
    val description: String,
    val category: Category
) {
    val color: Color
        get() = category.color
}

@HiltViewModel
class SkillsViewModel @Inject constructor() : ViewModel() {
    // Mutable state list of skills that can be observed by the UI
    private val initialSkills = listOf(
        Skill("Soccer", "Learn the basics of playing soccer.", Category.Sports),
        Skill(
            "C# Beginner Course",
            "Dive into the fascinating world of programming.",
            Category.Programming
        ),
        Skill("Stock Market Tips", "Learn how to succeed in the stock market.", Category.Finance),
        Skill("Painting", "From basics to advanced techniques.", Category.Art),
        Skill(
            "Music Theory",
            "Learn to play an instrument or understand music theory.",
            Category.Music
        ),
        Skill("English Language & Literature", "Improve your English skills.", Category.Languages),
        Skill("Nutrition Tips", "Learn how to live healthier.", Category.Health),
        Skill("Basketball", "Explore basketball techniques and training.", Category.Sports),
        Skill("JavaScript Essentials",
            "Learn the fundamentals of JavaScript.",
            Category.Programming
        ),
        Skill(
            "Investment Strategies",
            "Understand different approaches to investing.",
            Category.Finance
        ),
        Skill("Digital Art", "Create art using digital tools and software.", Category.Art),
        Skill("Guitar Lessons", "Beginner to advanced guitar lessons.", Category.Music),
        Skill("Spanish for Beginners", "Start learning Spanish today!", Category.Languages),
        Skill(
            "Yoga for Beginners",
            "Learn the basics of yoga for health and relaxation.",
            Category.Fitness
        )
    )

    private val _skills = MutableStateFlow<List<Skill>>(initialSkills)
    val skills: StateFlow<List<Skill>> = _skills

    // Method to add a new skill to the list
    fun addSkill(skill: Skill) {
        viewModelScope.launch {
            _skills.value = listOf(skill) + _skills.value
        }

    }
}