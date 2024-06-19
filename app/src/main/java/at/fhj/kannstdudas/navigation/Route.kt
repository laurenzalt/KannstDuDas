package at.fhj.kannstdudas.navigation

import kotlinx.serialization.Serializable

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 19/06/2024
 */

@Serializable
sealed class Route() {
    @Serializable
    data object AuthNav: Route()
    @Serializable
    data object HomeNav: Route()
}