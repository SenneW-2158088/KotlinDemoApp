package com.example.kotlingdemoapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


enum class DemoScreen(@StringRes val title: Int){
    Login(title = R.string.login_screen_title),
    ChatOverview(title = R.string.chatoverview_screen_title)
}

@Composable
fun DemoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DemoScreen.Login.name,
            modifier = modifier.padding(innerPadding)
        ){
            composable(route = DemoScreen.Login.name){
                LoginScreen(
                    onNextButtonClicked = {navController.navigate(DemoScreen.ChatOverview.name)}
                )
            }
            composable(route = DemoScreen.ChatOverview.name){
                ChatOverviewScreen()
            }
        }
    }
}