package com.example.kotlingdemoapp.ui.userSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlingdemoapp.di.SessionHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.RoomService
import org.matrix.android.sdk.api.session.user.UserService

class UserSearchViewModel : ViewModel(){
    private val session : Session = SessionHolder.currentSession!!
    private var userService: UserService = session.userService();
    private var roomService: RoomService = session.roomService();
    private val searchLimit: Int = 30

    private var _userSearchState : MutableStateFlow<UserSearchState> = MutableStateFlow(
        UserSearchState()
    )
    val userSearchState : StateFlow<UserSearchState>
        get() = _userSearchState

    init{
    }

    public fun searchUsers(userId: String):Unit{
        viewModelScope.launch {
            _userSearchState.update{
                currentState ->
                currentState.copy(users = userService.searchUsersDirectory(userId, 30, emptySet()))
            }
        }
    }

    fun createChat(userId: String, onRoomCreated: (String) -> Unit) {
        viewModelScope.launch {
            val roomId :String = roomService.createDirectRoom(userId);
            onRoomCreated(roomId);
        }
    }
}