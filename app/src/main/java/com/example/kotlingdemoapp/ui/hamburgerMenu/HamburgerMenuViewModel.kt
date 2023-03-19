package com.example.kotlingdemoapp.ui.hamburgerMenu

import androidx.lifecycle.ViewModel
import com.example.kotlingdemoapp.SessionHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.matrix.android.sdk.api.session.Session

class HamburgerMenuViewModel : ViewModel() {
    val session : Session = SessionHolder.currentSession!!

    private var _hamburgerMenuUiState : MutableStateFlow<HamburgerMenuUIState> = MutableStateFlow(
        HamburgerMenuUIState()
    )
    val hamburgerMenuUiState : StateFlow<HamburgerMenuUIState>
        get() = _hamburgerMenuUiState

    init {

    }
}