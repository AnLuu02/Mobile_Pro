package com.example.jetpackcomposedemo.Screen.GlobalScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun VideoScreen(
  navController: NavHostController? = null
) {
  val context = LocalContext.current

  val exoPlayer = remember {
    ExoPlayer.Builder(context).build().apply {
      setVideoItem(context)
      playWhenReady = true

      addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
          if (state == Player.STATE_ENDED) {
            navController?.navigate("home")
          }
        }
      })
    }
  }

  DisposableEffect(Unit) {
    onDispose {
      exoPlayer.release()
    }
  }

  AndroidView(
    factory = { ctx ->
      PlayerView(ctx).apply {
        player = exoPlayer
        useController = false
        resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
        layoutParams = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams(
          androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_PARENT,
          androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_PARENT
        )
      }
    },
    modifier = Modifier.fillMaxSize()
  )
}

// Set up component
private fun ExoPlayer.setVideoItem(context: android.content.Context) {
  val rawResourceUri = "android.resource://${context.packageName}/${R.raw.starting_app}"
  val mediaItem = MediaItem.fromUri(rawResourceUri.toUri())
  setMediaItem(mediaItem)
  prepare()
}

@Preview(showBackground = true)
@Composable
fun demoVideoScreen() {
  VideoScreen()
}
