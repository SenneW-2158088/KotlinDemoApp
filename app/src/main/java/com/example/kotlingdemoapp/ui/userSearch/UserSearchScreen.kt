package com.example.kotlingdemoapp.ui.userSearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlingdemoapp.ui.theme.DemoTheme


@Composable
fun UserSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: UserSearchViewModel = viewModel(),
    onRoomCreated: (String) -> Unit
){
    Column() {
        SearchInput(viewModel)
        Results(viewModel, onRoomCreated)
    }
}

@Preview
@Composable
private fun UserSearchPreview(){
    DemoTheme() {
        SearchInput(viewModel());
    }
}

@Composable
fun SearchInput(viewModel: UserSearchViewModel){
    var inputValue:String by remember{mutableStateOf("")}
    TextField(
        value = inputValue,
        onValueChange = { inputValue = it; viewModel.searchUsers(it) },
        modifier = Modifier.fillMaxWidth()
    );
}



@Composable
fun Results(viewModel:UserSearchViewModel, onRoomCreated: (String) -> Unit){
    val state: UserSearchState by viewModel.userSearchState.collectAsState();
    LazyColumn(){
        items(state.users){
            user -> UserInstance(userId = user.userId, onClick={userId: String -> viewModel.createChat(userId, onRoomCreated); })
        }
    }
}

@Composable
fun UserInstance(userId:String, onClick: (String) -> Unit){
    Text(
        text = userId,
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick(userId) },
        color = MaterialTheme.colors.onBackground
    )
}