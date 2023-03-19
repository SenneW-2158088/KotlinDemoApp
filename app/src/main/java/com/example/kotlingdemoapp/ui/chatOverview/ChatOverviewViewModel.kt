package com.example.kotlingdemoapp.ui.chatOverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlingdemoapp.SessionHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.query.RoomCategoryFilter
import org.matrix.android.sdk.api.query.SpaceFilter
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams
import timber.log.Timber

class ChatOverviewViewModel : ViewModel() {
    val session : Session = SessionHolder.currentSession!!

    private var _roomsUiState : MutableStateFlow<ChatOverviewUIState> = MutableStateFlow(
        ChatOverviewUIState()
    )
    val roomsUiState : StateFlow<ChatOverviewUIState>
        get() = _roomsUiState

    init {
        // Create query to listen to room summary list
        val roomSummariesQuery = roomSummaryQueryParams {
            memberships = Membership.activeMemberships()
        }
        viewModelScope.launch {
            session.roomService().getRoomSummariesLive(roomSummariesQuery).observeForever(){
                // all methods of state-flow are thread-safe (https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
                rooms -> setRooms(rooms);
            }
        }
        Timber.tag("A NOTIFICATIONS??").d("NOTFICATIONS HERE::: %s", session.roomService().getNotificationCountForRooms(roomSummariesQuery).notificationCount)
    }

    private fun setRooms(rooms : List<RoomSummary>){
        _roomsUiState.update {
            currentState ->
                currentState.copy(rooms = rooms)
        }
    }
}