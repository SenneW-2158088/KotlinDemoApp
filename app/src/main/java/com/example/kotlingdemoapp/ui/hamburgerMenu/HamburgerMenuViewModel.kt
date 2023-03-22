package com.example.kotlingdemoapp.ui.hamburgerMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlingdemoapp.SessionHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.content.ContentUrlResolver
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.spaceSummaryQueryParams
import org.matrix.android.sdk.api.session.profile.ProfileService

class HamburgerMenuViewModel : ViewModel() {
    val session : Session = SessionHolder.currentSession!!
    private var _hamburgerMenuUiState : MutableStateFlow<HamburgerMenuUIState> = MutableStateFlow(
        HamburgerMenuUIState()
    )
    val hamburgerMenuUiState : StateFlow<HamburgerMenuUIState>
        get() = _hamburgerMenuUiState
    private val profileService : ProfileService = session.profileService()

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
            _hamburgerMenuUiState.update { currentState ->
                currentState.copy(userName = profileService.getDisplayName(session.myUserId).get(),
                                userDomain = session.myUserId)
            }
        }
        viewModelScope.launch {
            var avatarUrl : String = profileService.getAvatarUrl(session.myUserId).get();
            var resolvedImage = session.contentUrlResolver()
                .resolveThumbnail(
                    avatarUrl,
                    250,
                    250,
                    ContentUrlResolver.ThumbnailMethod.SCALE
                )
            _hamburgerMenuUiState.update { currentState ->
                currentState.copy(resolvedImageURL = resolvedImage)
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