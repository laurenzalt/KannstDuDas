package at.fhj.kannstdudas.domain.model

import androidx.compose.ui.graphics.Color
import at.fhj.kannstdudas.domain.model.Category

data class Skill(
    val name: String,
    val description: String,
    val category: Category
) {
    val color: Color
        get() = category.color
}
