package com.example.kotlingdemoapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlingdemoapp.ui.chatOverview.ChatOverviewUIState
import com.example.kotlingdemoapp.ui.chatOverview.ChatOverviewViewModel
import com.example.kotlingdemoapp.ui.theme.DemoTheme
import org.matrix.android.sdk.api.session.events.model.toModel
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.model.message.MessageContent
import org.matrix.android.sdk.api.session.room.timeline.TimelineEvent
import timber.log.Timber


@Composable
fun ChatOverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatOverviewViewModel = viewModel(),
    onRoomClicked : (roomId : String) -> Unit,
    onSearchClicked : () -> Unit
){
    val roomState:ChatOverviewUIState by viewModel.roomsUiState.collectAsState();
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Rooms(roomState = roomState, onRoomClicked = onRoomClicked)
        FloatingActionButton(
            onClick = {onSearchClicked()},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
        ) {

        }
    }
}

fun Modifier.badgeLayout() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // based on the expectation of only one line of text
        val minPadding = placeable.height / 4

        val width = maxOf(placeable.width + minPadding, placeable.height)
        layout(width, placeable.height) {
            placeable.place((width - placeable.width) / 2, 0)
        }
    }
@Composable
fun Rooms(roomState: ChatOverviewUIState, onRoomClicked : (roomId : String) -> Unit){
    LazyColumn(){
        items(roomState.rooms?: emptyList()){
            Row(modifier= Modifier
                .background(color = MaterialTheme.colors.background)
                .padding(4.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = it.displayName,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .clickable { onRoomClicked(it.roomId) }
                )
                Timber.tag("NOTIFICATION!!!").d("%s", it.notificationCount)
//                val item :MessageContent? = it.latestPreviewableEvent?.root?.getClearContent().toModel<MessageContent>(false);
//                Text(
//                    text = item?.body?:"",
//                    modifier = Modifier.size(12.dp),
//                    color = MaterialTheme.colors.onBackground
//                )
                Row{
                    Text(
                        text = it.notificationCount.toString(),
                        modifier = Modifier
                            .background(Color(0xFF86A0EE), shape = CircleShape)
                            .badgeLayout()
                    )
                    // THESE NO WORKY?
//                    Text(
//                        text = it.highlightCount.toString(),
//                        modifier = Modifier
//                            .background(Color(0xFF86A0EE), shape = CircleShape)
//                            .badgeLayout()
//                    )
//                    Text(
//                        text = it.threadNotificationCount.toString(),
//                        modifier = Modifier
//                            .background(Color(0xFF86A0EE), shape = CircleShape)
//                            .badgeLayout()
//                    )
//                    Text(
//                        text = it.threadHighlightCount.toString(),
//                        modifier = Modifier
//                            .background(Color(0xFF86A0EE), shape = CircleShape)
//                            .badgeLayout()
//                    )
                }
            }
        }
    }
}

@Preview(name = "Preview Overview")
@Composable
private fun OverviewPreview(){
    val state:ChatOverviewUIState = ChatOverviewUIState(listOf(RoomSummary("1", "bla bla", "ada", "daldjf", encryptionEventTs = Long.MAX_VALUE, isEncrypted = false, typingUsers = listOf(), notificationCount = 5)))
    DemoTheme (darkTheme = true) {
        Rooms(roomState = state, onRoomClicked = {})
    }
}