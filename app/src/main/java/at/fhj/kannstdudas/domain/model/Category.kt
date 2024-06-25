package at.fhj.kannstdudas.domain.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import at.fhj.kannstdudas.R

/**
 * at.fhj.kannstdudas.domain.model
 * Created by Laurenz Altendorfer on 16/06/2024
 */

enum class Category(@StringRes val label: Int, val color: Color) {
    Programming(R.string.programming, Color(0xFFAED581)),
    Sports(R.string.sports, Color(0xFFFFD54F)),
    Finance(R.string.finance, Color(0xFF4FC3F7)),
    Art(R.string.art, Color(0xFFF06292)),
    Music(R.string.music, Color(0xFF2D2C7D)),
    Languages(R.string.languages, Color(0xFFBA68C8)),
    Health(R.string.health, Color(0xFFFF8A65)),
    Fitness(R.string.fitness, Color(0xFF4DB6AC)),
    Other(R.string.other, Color(0xFF90A4AE))
}