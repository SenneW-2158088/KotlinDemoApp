package com.example.kotlingdemoapp.ui.userSearch

import org.matrix.android.sdk.api.session.user.model.User

data class UserSearchState (
    val users: List<User> = listOf(),
)