package com.example.videoplayer_kt

import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.videoplayer_kt.presentation.player.PlayerFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.UnstableApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Se llama cuando el usuario pulsa el botón de inicio del móvil
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        Log.d("MainActivity", "onUserLeaveHint llamado, isPlayerVisible: ${isPlayerVisible()}")
        // Solo entramos en PiP si estamos en el PlayerFragment
        if (isPlayerVisible()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val params = PictureInPictureParams.Builder()
                    .setAspectRatio(Rational(16, 9))
                    .build()
                enterPictureInPictureMode(params)
            }
        }
    }
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop llamado, isPlayerVisible: ${isPlayerVisible()}")
    }

    // Comprueba si el fragment visible actualmente es el PlayerFragment
    @OptIn(UnstableApi::class)
    private fun isPlayerVisible(): Boolean {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHost?.childFragmentManager?.primaryNavigationFragment is PlayerFragment
    }
}