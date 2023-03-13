package com.example.kotlingdemoapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kotlingdemoapp.ui.theme.DemoTheme
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration
import com.example.kotlingdemoapp.util.RoomDisplayNameFallbackProviderImpl
import timber.log.Timber

class MainActivity : ComponentActivity() {

    // instance of the matrix sdk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // logging system (https://github.com/JakeWharton/timber)
        // Installation of a "Tree", now you can log in whichever class you want, and the log will know in which class it is.
        Timber.plant(Timber.DebugTree())
        // You should first create a Matrix instance before using it
        createMatrix()
        // You can then grab the authentication service and search for a known session
        // authenticationService docs: https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api.auth/-authentication-service/index.html
        // session: https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api.session/-session/index.html
        val lastSession = matrix.authenticationService().getLastAuthenticatedSession()
        if (lastSession != null) {
            SessionHolder.currentSession = lastSession
            // Don't forget to open the session and start syncing.
            lastSession.open()
            lastSession.syncService().startSync(true)
        }
        setContent {
            DemoTheme {
                DemoApp()
            }
        }
    }

    private fun createMatrix() {
        // Matrix documentation: https://matrix-org.github.io/matrix-android-sdk2/
        // or https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api/-matrix/index.html
        // it is recommended to store this as a singleton (see companion object below)
        matrix = Matrix(
            context = this,
            // configuration doc: https://matrix-org.github.io/matrix-android-sdk2/matrix-sdk-android/org.matrix.android.sdk.api/-matrix-configuration/index.html
            matrixConfiguration = MatrixConfiguration(
                roomDisplayNameFallbackProvider = RoomDisplayNameFallbackProviderImpl()
            )
        )
    }

    companion object {
//        fun getMatrix(context: Context): Matrix {
//            return (context.applicationContext as MainActivity).matrix
//        }
        private lateinit var matrix: Matrix
        fun getMatrix(): Matrix {
            return this.matrix
        }
    }
}
