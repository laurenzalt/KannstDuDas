package at.fhj.kannstdudas.domain.model

import androidx.compose.ui.graphics.Color
import at.fhj.kannstdudas.domain.model.Category

/**
 * at.fhj.kannstdudas.domain.model
 * Created by Laurenz Altendorfer on 16/06/2024
 */

data class Skill(
    val name: String,
    val description: String,
    val category: Category
) {
    val color: Color
        get() = category.color
}
