package org.pursuit.wordtrivia.audio

import android.content.Context
import android.media.MediaPlayer
import org.pursuit.wordtrivia.R

class BackgroundMusic(private val context: Context) : Runnable {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    override fun run() {
        mediaPlayer = MediaPlayer.create(context, R.raw.gameplay_music)
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)

    }
}