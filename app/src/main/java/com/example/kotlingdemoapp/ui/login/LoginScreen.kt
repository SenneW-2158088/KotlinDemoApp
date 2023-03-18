package com.example.kotlingdemoapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlingdemoapp.ui.login.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked : () -> Unit
){
    val loginViewModel : LoginViewModel = viewModel()
    val uiState by loginViewModel.uiState.collectAsState()

    Column() {
        TextField(value = uiState.homeserver, onValueChange = {loginViewModel.setHomeserver(it)})
        TextField(value = uiState.username, onValueChange = {loginViewModel.setUsername(it)})
        TextField(value = uiState.password, onValueChange = {loginViewModel.setPassword(it)})
        Button(onClick = { loginViewModel.launchAuthProcess(onNextButtonClicked); }) {
        }
    }
}