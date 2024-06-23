package at.fhj.kannstdudas.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.User
import at.fhj.kannstdudas.domain.usecase.user.CheckAuthStatusUseCase
import at.fhj.kannstdudas.domain.usecase.user.GetCurrentUserUseCase
import at.fhj.kannstdudas.domain.usecase.user.ResetPasswordUseCase
import at.fhj.kannstdudas.domain.usecase.user.SignInUseCase
import at.fhj.kannstdudas.domain.usecase.user.SignOutUseCase
import at.fhj.kannstdudas.domain.usecase.user.SignUpUseCase
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
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private  val checkAuthStatusUseCase: CheckAuthStatusUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        _isSignedIn.value = checkAuthStatusUseCase.invoke()
        if (_isSignedIn.value) {
            fetchCurrentUser()
        }
    }

    fun setUserEmail(email: String) {
        _user.value = _user.value?.copy(email = email)
            ?: User(email = email, username = "", password = "")
    }

    fun setUserPassword(password: String) {
        _user.value = _user.value?.copy(password = password)
            ?: User(email = "", username = "", password = password)
    }

    fun setUsername(username: String) {
        _user.value = _user.value?.copy(username = username)
            ?: User(email = "", username = username, password = "")
    }

    fun signIn() {
        val user = _user.value ?: return
        viewModelScope.launch {
            try {
                signInUseCase(user)
                _isSignedIn.value = true
                _errorMessage.value = "Sign in successful"
                fetchCurrentUser()
            } catch (e: Exception) {
                _isSignedIn.value = false
                _errorMessage.value = e.message
            }
        }
    }

    fun signUp() {
        val user = _user.value ?: return
        viewModelScope.launch {
            try {
                signUpUseCase(user)
                _errorMessage.value = "Sign up successful"
                fetchCurrentUser()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun resetPassword() {
        val email = _user.value?.email ?: return
        viewModelScope.launch {
            try {
                resetPasswordUseCase(User(email = email, username = "", password = ""))
                _errorMessage.value = "Reset password E-Mail sent"
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            try {
                signOutUseCase()
                _user.value = null
                _isSignedIn.value = false
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun uploadProfilePicture(uri: Uri) {
        viewModelScope.launch {
            try {
                val url = userRepository.uploadProfilePicture(uri)
                userRepository.uploadProfilePicture(url)
                fetchCurrentUser()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val currentUser = getCurrentUserUseCase()
                _user.value = currentUser
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}