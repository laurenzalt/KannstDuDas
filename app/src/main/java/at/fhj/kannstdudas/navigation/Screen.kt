package at.fhj.kannstdudas.navigation

import kotlinx.serialization.Serializable

/**
 * at.fhj.kannstdudas.navigation
 * Created by Noah Dimmer on 12/06/2024
 */

@Serializable
sealed class Screen(val id: String) {
    @Serializable
    data object Explore: Screen("Explore")
    @Serializable
    data object MySkills: Screen("My Skills")
    @Serializable
    data object NewSkill: Screen("New Skill")
    @Serializable
    data object Profile: Screen("Profile")
    @Serializable
    data object SignIn: Screen("Sign in")
    @Serializable
    data object  SignUp: Screen("Sign up")
    @Serializable
    data object  ForgotPassword: Screen("Forgot Password")
}