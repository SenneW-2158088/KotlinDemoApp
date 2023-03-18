package com.example.kotlingdemoapp.ui.userSearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UserSearchScreen(
    modifier: Modifier = Modifier
){

    Column() {

    }
}

@Composable
fun Results(){
    Row{
        UserInstance(username = "blob")
    }
}

@Composable
fun UserInstance(username:String){
    Text(
        text = username,
        modifier = Modifier.fillMaxWidth()
    )
}