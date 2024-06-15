package at.fhj.kannstdudas.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import at.fhj.kannstdudas.model.Skill
import at.fhj.kannstdudas.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor() : ViewModel() {
    // Mutable state list of skills that can be observed by the UI
    val _skills = mutableStateListOf<Skill>(
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

    // Method to add a new skill to the list
    fun addSkill(skill: Skill) {
        _skills.add(0, skill)  // Adds to the beginning of the list
    }
}