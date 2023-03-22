package com.example.kotlingdemoapp.ui.chatOverview

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.kotlingdemoapp.SessionHolder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams


class ChatOverviewViewModel : ViewModel() {
    val session : Session = SessionHolder.currentSession!!

    val roomSummariesQuery = roomSummaryQueryParams {
        memberships = Membership.activeMemberships()
    }

    private var _roomsUiState : StateFlow<RoomsUIState> = session.roomService().getRoomSummariesLive(roomSummariesQuery).asFlow()
        .map ( RoomsUIState::Success )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RoomsUIState.Loading,
        )


    val roomsUiState : StateFlow<RoomsUIState>
        get() = _roomsUiState

    init {
        // Create query to listen to room summary list
//        val roomSummariesQuery = roomSummaryQueryParams {
//            memberships = Membership.activeMemberships()
//        }
//        viewModelScope.launch {
//            _roomsUiState =
//        }
    }
}