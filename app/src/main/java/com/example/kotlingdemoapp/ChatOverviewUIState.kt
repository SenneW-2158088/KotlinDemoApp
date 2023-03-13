package com.example.kotlingdemoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.matrix.android.sdk.api.session.room.model.RoomSummary

data class ChatOverviewUIState (
    val rooms : List<RoomSummary> = listOf()
)