package com.example.kotlingdemoapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kotlingdemoapp.ui.chatRoom.ChatRoomScreen
import com.example.kotlingdemoapp.ui.chatRoom.ChatRoomViewModel
import com.example.kotlingdemoapp.ui.userSearch.UserSearchScreen


enum class DemoScreen(@StringRes val title: Int){
    Login(title = R.string.login_screen_title),
    ChatOverview(title = R.string.chatoverview_screen_title),
    UserSearch(title = R.string.user_search),
    ChatRoom(title = R.string.chat_room)
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
                ChatOverviewScreen(
                    onRoomClicked = { roomId : String -> navController.navigate(DemoScreen.ChatRoom.name + "/" + roomId)},
                    onSearchClicked = { navController.navigate(DemoScreen.UserSearch.name) }
                )
            }
            composable(route = DemoScreen.UserSearch.name){
                UserSearchScreen(
                    onRoomCreated = {roomId: String -> navController.navigate(DemoScreen.ChatRoom.name + "/" + roomId)}
                )
            }
            composable(
                route = DemoScreen.ChatRoom.name + "/{roomId}",
                arguments = listOf(navArgument("roomId")
                    {
                        type = NavType.StringType;
                        defaultValue = "0";
                        nullable = true;
                    }
                )
            ){ backStackEntry ->
                ChatRoomScreen(
                    Modifier,
                    ChatRoomViewModel(roomId = backStackEntry.arguments?.getString("roomId") ?: "")
                )
            }
        }
    }
}