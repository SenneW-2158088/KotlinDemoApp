package com.example.kotlingdemoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.matrix.android.sdk.api.session.room.model.RoomSummary

class ChatOverviewViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ChatOverviewUIData())
    val uiState : StateFlow<ChatOverviewUIData> = _uiState.asStateFlow()

    fun getRooms() : List<RoomSummary>{
        return _uiState.value.rooms
    }
}