package com.example.kotlingdemoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun ChatOverviewScreen(
    modifier: Modifier = Modifier,
){
    // Create query to listen to room summary list
    val roomSummariesQuery = roomSummaryQueryParams {
        memberships = Membership.activeMemberships()
    }
    val session : Session = SessionHolder.currentSession!!
    // Then you can subscribe to livedata..
    val roomState by session.roomService().getRoomSummariesLive(roomSummariesQuery).observeAsState();

    Column() {
        LazyColumn(){
            items(roomState?: emptyList()){
                Box(modifier=Modifier.background(color = Color(0, 255, 0))){
                    Text(text = "room.name" + it.displayName)
                }
            }
        }
    }
}
