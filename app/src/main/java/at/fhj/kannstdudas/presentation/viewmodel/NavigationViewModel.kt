package at.fhj.kannstdudas.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.presentation.viewmodel
 * Created by Noah Dimmer on 23/06/2024
 */

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
    private val _selectedItem = mutableStateOf<Int?>(0)
    val selectedItem: MutableState<Int?> = _selectedItem

    private val _profileSelected = mutableStateOf(false)
    val profileSelected: MutableState<Boolean> = _profileSelected

    fun setSelectedItem(index: Int) {
        _selectedItem.value = index
    }

    fun selectProfile() {
        _profileSelected.value = true
        _selectedItem.value = null
    }

    fun clearProfileSelection() {
        _profileSelected.value = false
    }
}
