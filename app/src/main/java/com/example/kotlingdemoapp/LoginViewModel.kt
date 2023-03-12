package com.example.kotlingdemoapp

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig
import org.matrix.android.sdk.api.session.Session

class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState : StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun setHomeserver(homeserver : String){
        _uiState.update{
            currentState ->
                currentState.copy(homeserver = homeserver)
        }
    }

    fun setUsername(username : String){
        _uiState.update{
                currentState ->
            currentState.copy(username = username)
        }
    }

    fun setPassword(password : String){
        _uiState.update{
                currentState ->
            currentState.copy(password = password)
        }
    }

    public fun launchAuthProcess() {
        val username = _uiState.value.username
        val password = _uiState.value.password
        val homeserver = _uiState.value.homeserver

        // First, create a homeserver config
        // Be aware than it can throw if you don't give valid info
        // necessary for the `directionAuthentication` further on: https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api.auth/-authentication-service/direct-authentication.html
        val homeServerConnectionConfig = try {
            HomeServerConnectionConfig
                .Builder()
                .withHomeServerUri(Uri.parse(homeserver))
                .build()
        } catch (failure: Throwable) {
            // gives a little popup at the bottom of the application
            println("Failed to log in");
            return
        }

        runBlocking{
            launch{
                try {
                    var session : Session = MainActivity.getMatrix() // singleton
                        .authenticationService().directAuthentication(
                            homeServerConnectionConfig,
                            username,
                            password,
                            "matrix-sdk-android2-sample"
                        )
                    SessionHolder.currentSession = session
                    // same stuff that happens in SampleApp
                    session.open()
                    session.syncService().startSync(true)
                    println("Logged in!");
                } catch (failure: Throwable) {
                    println("Something failed 2");
                }
            }
        }
        // Then you can retrieve the authentication service.
        // Here we use the direct authentication, but you get LoginWizard and RegistrationWizard for more advanced feature
        // Direct authentication: https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api.auth/-authentication-service/direct-authentication.html
        // LoginWizard: https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api.auth.login/-login-wizard/index.html
        // RegistrationWizard: https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api.auth.registration/-registration-wizard/index.html
    }
}