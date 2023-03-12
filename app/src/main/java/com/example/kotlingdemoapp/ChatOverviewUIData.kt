package com.example.kotlingdemoapp

import org.matrix.android.sdk.api.session.room.model.RoomSummary

data class ChatOverviewUIData (
    val rooms : List<RoomSummary> = listOf()
)
