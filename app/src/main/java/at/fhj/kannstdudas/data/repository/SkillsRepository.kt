package at.fhj.kannstdudas.data.repository

import at.fhj.kannstdudas.domain.model.Skill
import at.fhj.kannstdudas.domain.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkillsRepository @Inject constructor() {
    private val _skills = MutableStateFlow<List<Skill>>(emptyList())

    val skills: StateFlow<List<Skill>> = _skills

    fun addSkill(skill: Skill) {
        val updatedList = listOf(skill) + _skills.value
        _skills.value = updatedList
    }

    init {
        loadInitialSkills()
    }

    private fun loadInitialSkills() {
        _skills.value = listOf(
            Skill("Soccer", "Learn the basics of playing soccer.", Category.Sports),
            Skill("C# Beginner Course", "Dive into the fascinating world of programming.", Category.Programming),
            Skill("Stock Market Tips", "Learn how to succeed in the stock market.", Category.Finance),
            Skill("Painting", "From basics to advanced techniques.", Category.Art),
            Skill("Music Theory", "Learn to play an instrument or understand music theory.", Category.Music),
            Skill("English Language & Literature", "Improve your English skills.", Category.Languages),
            Skill("Nutrition Tips", "Learn how to live healthier.", Category.Health),
            Skill("Basketball", "Explore basketball techniques and training.", Category.Sports),
            Skill("JavaScript Essentials", "Learn the fundamentals of JavaScript.", Category.Programming),
            Skill("Investment Strategies", "Understand different approaches to investing.", Category.Finance),
            Skill("Digital Art", "Create art using digital tools and software.", Category.Art),
            Skill("Guitar Lessons", "Beginner to advanced guitar lessons.", Category.Music),
            Skill("Spanish for Beginners", "Start learning Spanish today!", Category.Languages),
            Skill("Yoga for Beginners", "Learn the basics of yoga for health and relaxation.", Category.Fitness)
        )
    }
}
