package com.example.kotlingdemoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlingdemoapp.ui.chatOverview.ChatOverviewViewModel

@Composable
fun ChatOverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatOverviewViewModel = viewModel()
){
    val roomState by viewModel.roomsUiState.collectAsState();
    LazyColumn(){
        items(roomState.rooms?: emptyList()){
            Box(modifier=Modifier.background(color = Color(0, 255, 0))){
                Text(text = "room.name" + it.displayName)
            }
        }
    }
}
