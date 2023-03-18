package com.example.kotlingdemoapp.ui.chatRoom

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlingdemoapp.SessionHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.events.model.toModel
import org.matrix.android.sdk.api.session.getRoom
import org.matrix.android.sdk.api.session.room.Room
import org.matrix.android.sdk.api.session.room.model.message.MessageContent
import org.matrix.android.sdk.api.session.room.read.ReadService
import org.matrix.android.sdk.api.session.room.timeline.Timeline
import org.matrix.android.sdk.api.session.room.timeline.TimelineEvent
import org.matrix.android.sdk.api.session.room.timeline.TimelineSettings

class ChatRoomViewModel(val roomId: String) : ViewModel(), Timeline.Listener {
    val session : Session = SessionHolder.currentSession!!
    private var timeline: Timeline? = null

    private var room: Room? = null;

    private var _chatsUiState : MutableStateFlow<ChatState> = MutableStateFlow(ChatState());
    val chatsUiState = _chatsUiState

    init{
        room = session.getRoom(roomId)
        viewModelScope.launch {
            room?.readService()?.markAsRead(ReadService.MarkAsReadParams.READ_RECEIPT)
        }
        val timelineSettings = TimelineSettings(
            initialSize = 30
        )
        // Then you can retrieve a timeline from this room.
        timeline = room?.timelineService()?.createTimeline(null, timelineSettings)?.also {
            // Don't forget to add listener and start the timeline so it start listening to changes
            it.addListener(this)
            it.start()
        }
    }

    fun sendMessage(message:String){
        room?.sendService()?.sendTextMessage(message);
    }

    // === TIMELINE METHOD OVERRIDES ===
    override fun onNewTimelineEvents(eventIds: List<String>) {
        // This is new event ids coming from sync
    }

    override fun onTimelineFailure(throwable: Throwable) {
        // When a failure is happening when trying to retrieve an event.
        // This is an unrecoverable error, you might want to restart the timeline
        // timeline?.restartWithEventId("")
    }

    override fun onTimelineUpdated(snapshot: List<TimelineEvent>) {
        // Each time the timeline is updated it will be called.
        // It can happens when sync returns, paginating, and updating (local echo, decryption finished...)
        // You probably want to process with DiffUtil before dispatching to your recyclerview
        val size : Int = snapshot.count();
        println("SIZE OF THE SNAPCHOT::::" + size);
        var messages = mutableListOf<MessageContent>()
        for (event in snapshot){
            var message = event.root.getClearContent().toModel<MessageContent>(false) ?: continue;
            Log.d("EVENT BABY", "EVENT BABY????: " + message.body)
            messages.add(message)
        }
        _chatsUiState.update {
            currentState ->
                currentState.copy(messages = messages)
        }
    }
}