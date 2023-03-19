package com.example.kotlingdemoapp.ui.hamburgerMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlingdemoapp.SessionHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams
import org.matrix.android.sdk.api.session.room.spaceSummaryQueryParams

class HamburgerMenuViewModel : ViewModel() {
    val session : Session = SessionHolder.currentSession!!

    private var _hamburgerMenuUiState : MutableStateFlow<HamburgerMenuUIState> = MutableStateFlow(
        HamburgerMenuUIState()
    )
    val hamburgerMenuUiState : StateFlow<HamburgerMenuUIState>
        get() = _hamburgerMenuUiState

    init {
        val spaceSummariesLive = spaceSummaryQueryParams{
            memberships = Membership.activeMemberships()
        }
        viewModelScope.launch {
            session.spaceService().getSpaceSummariesLive(spaceSummariesLive).observeForever {
                spaces -> setSpaces(spaces)
            }
        }
    }

    private fun setSpaces(spaces: List<RoomSummary>){
        _hamburgerMenuUiState.update {
            current ->
                current.copy(spaces = spaces)
        }
    }

}