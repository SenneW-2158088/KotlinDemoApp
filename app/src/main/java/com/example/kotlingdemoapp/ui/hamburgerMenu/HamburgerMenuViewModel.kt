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
import org.matrix.android.sdk.api.session.profile.ProfileService

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
            session.spaceService().getSpaceSummariesLive(spaceSummariesLive)
                .observeForever { spaces ->
                    setSpaces(spaces)
                }
        }
        viewModelScope.launch {
            var profileService : ProfileService = session.profileService()
            _hamburgerMenuUiState.update { currentState ->
                currentState.copy(userName = profileService.getDisplayName(session.myUserId).get(),
                                userDomain = session.myUserId)
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