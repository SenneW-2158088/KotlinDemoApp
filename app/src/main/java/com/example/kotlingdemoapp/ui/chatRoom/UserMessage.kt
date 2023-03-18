package com.example.kotlingdemoapp.ui.chatRoom

import org.matrix.android.sdk.api.session.room.model.message.MessageContent

data class UserMessage(
    var messageContent : MessageContent,
    var profileUrl: String = ""
)
