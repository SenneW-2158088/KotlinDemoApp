package com.example.kotlingdemoapp.ui.chatOverview

import org.matrix.android.sdk.api.session.room.model.RoomSummary

sealed interface RoomsUIState {
    /**
     * The feed is still loading.
     */
    object Loading : RoomsUIState

    /**
     * The feed is loaded with the given list of news resources.
     */
    data class Success(
        /**
         * The list of news resources contained in this feed.
         */
        val rooms: List<RoomSummary>,
    ) : RoomsUIState
}