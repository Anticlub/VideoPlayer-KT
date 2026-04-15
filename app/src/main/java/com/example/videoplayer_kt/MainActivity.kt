package com.example.videoplayer_kt

import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.videoplayer_kt.presentation.main.MainViewModel
import com.example.videoplayer_kt.presentation.player.PlayerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.pipEvent.collect {
                val params = PictureInPictureParams.Builder()
                    .setAspectRatio(Rational(16,9))
                    .build()
                enterPictureInPictureMode(params)
            }
        }
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHost?.childFragmentManager?.addOnBackStackChangedListener {
            val isPlayer = navHost.childFragmentManager
                .primaryNavigationFragment is PlayerFragment
            viewModel.onFragmentChanged(isPlayer)
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        viewModel.shouldEnterPip()
    }
}