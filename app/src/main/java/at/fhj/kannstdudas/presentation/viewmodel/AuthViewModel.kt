package at.fhj.kannstdudas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.domain.model.User
import at.fhj.kannstdudas.domain.usecase.GetCurrentUserUseCase
import at.fhj.kannstdudas.domain.usecase.ResetPasswordUseCase
import at.fhj.kannstdudas.domain.usecase.SignInUseCase
import at.fhj.kannstdudas.domain.usecase.SignOutUseCase
import at.fhj.kannstdudas.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Noah Dimmer on 13/06/2024
 */

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private  val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _currentUser = MutableStateFlow<String?>(null)
    val currentUser: StateFlow<String?> = _currentUser

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun signIn() {
        val user = User(email = email.value, username = "", password = password.value)
        viewModelScope.launch {
            try {
                signInUseCase(user)
                _isSignedIn.value = true
                _errorMessage.value = "Sign in successful"
            } catch (e: Exception) {
                _isSignedIn.value = false
                _errorMessage.value = e.message
            }
        }
    }

    fun signUp(username: String) {
        val user = User(email = email.value, username = username, password = password.value)
        viewModelScope.launch {
            try {
                signUpUseCase(user)
                _errorMessage.value = "Sign up successful"
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun resetPassword() {
        val user = User(email = email.value, username = "", password = password.value)
        viewModelScope.launch {
            try {
                resetPasswordUseCase(user)
                _errorMessage.value = "Reset password E-Mail sent"
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun fetchCurrentUser(): String {
        viewModelScope.launch {
            try {
                val currentUser = getCurrentUserUseCase()
                _currentUser.value = currentUser?.email
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
        return currentUser.value ?: "User not found"
    }

    fun logoutUser() {
        viewModelScope.launch {
            try {
                signOutUseCase()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}