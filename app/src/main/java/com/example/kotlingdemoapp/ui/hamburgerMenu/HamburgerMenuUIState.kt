package com.example.kotlingdemoapp.ui.hamburgerMenu

import org.matrix.android.sdk.api.session.room.model.RoomSummary


data class HamburgerMenuUIState(
    val userName: String = "",
    val userDomain: String = "",
    val spaces: List<RoomSummary> = emptyList(),
    val resolvedImageURL: String? = null
)
