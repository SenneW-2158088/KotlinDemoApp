package com.example.kotlingdemoapp.ui.chatRoom

import org.matrix.android.sdk.api.session.room.model.message.MessageContent

data class ChatState(
    val messages: List<UserMessage> = listOf(),
    val input: String = "",
)