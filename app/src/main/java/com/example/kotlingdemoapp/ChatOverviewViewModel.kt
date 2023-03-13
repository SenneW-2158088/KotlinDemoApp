package com.example.kotlingdemoapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams

class ChatOverviewViewModel : ViewModel() {
    val session : Session = SessionHolder.currentSession!!

    private var _roomsUiState : MutableStateFlow<ChatOverviewUIState> = MutableStateFlow(ChatOverviewUIState())
    val roomsUiState : StateFlow<ChatOverviewUIState>
        get() = _roomsUiState
    init {
        // Create query to listen to room summary list
        val roomSummariesQuery = roomSummaryQueryParams {
            memberships = Membership.activeMemberships()
        }
        viewModelScope.launch {
            session.roomService().getRoomSummariesLive(roomSummariesQuery).observeForever(){
                rooms -> setRooms(rooms);
            }
        }
    }

    private fun setRooms(rooms : List<RoomSummary>){
        _roomsUiState.update {
            currentState ->
                currentState.copy(rooms = rooms)
        }
    }
}