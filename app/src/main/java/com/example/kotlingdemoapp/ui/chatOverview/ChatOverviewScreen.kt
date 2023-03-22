package com.example.kotlingdemoapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlingdemoapp.ui.chatOverview.RoomsUIState
import com.example.kotlingdemoapp.ui.chatOverview.ChatOverviewViewModel
import com.example.kotlingdemoapp.ui.hamburgerMenu.HamburgerMenu
import com.example.kotlingdemoapp.ui.theme.DemoTheme
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import timber.log.Timber


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatOverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatOverviewViewModel = viewModel(),
    onRoomClicked : (roomId : String) -> Unit,
    onSearchClicked : () -> Unit
){
    val roomState by viewModel.roomsUiState.collectAsState();

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier,
                backgroundColor = MaterialTheme.colors.background,
                title = {
                    Text(text = "Chat Overview")
                },
                navigationIcon = {
                    IconButton( onClick = {
                        scope.launch{ scaffoldState.drawerState.open() }
                    } ) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "HamburgerMenu Icon")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "",
                            tint = Color.White,
                        )
                    }
                }
            )
        },
        drawerContent = { HamburgerMenu() },
    )
    {
        when (roomState){
            is RoomsUIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()){
                    println("SHIT IS LOADINNNGGG")
                    Text(text="SHIT IS LOADING MEN")
                }
            }
            is RoomsUIState.Success -> {
                Box(modifier = Modifier.fillMaxSize()){
                    Rooms(roomState = roomState as RoomsUIState.Success, onRoomClicked = onRoomClicked)
                    FloatingActionButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.BottomEnd)
                    ){

                    }
                }
            }
        }
    }
}

@Composable
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
fun Rooms(roomState: RoomsUIState.Success, onRoomClicked : (roomId : String) -> Unit){
    LazyColumn(){
        items(roomState.rooms){
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

//@Preview(name = "Preview Overview")
//@Composable
//private fun OverviewPreview(){
//    val state = listOf(RoomSummary("1", "bla bla", "ada", "daldjf", encryptionEventTs = Long.MAX_VALUE, isEncrypted = false, typingUsers = listOf(), notificationCount = 5))
//    DemoTheme (darkTheme = true) {
//        Rooms(roomState = state, onNextButtonClicked = {})
//    }
//}
