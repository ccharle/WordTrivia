package org.pursuit.wordtrivia.audio

import android.content.Context
import android.media.MediaPlayer
import org.pursuit.wordtrivia.R

class AudioLoader(
    private val context: Context,
    private var mediaPlayer: MediaPlayer
) {

    fun onSplashScreen(){
        mediaPlayer = MediaPlayer.create(context, R.raw.on_starting)
        mediaPlayer.start()
    }
    fun correctSound() {

        mediaPlayer = MediaPlayer.create(context, R.raw.correct_sound)
        mediaPlayer.start()


    }

    fun incorrectSound() {

        mediaPlayer = MediaPlayer.create(context, R.raw.wrong_answer)
        mediaPlayer.start()


    }

    fun releaseMediaPlayer() {
        mediaPlayer.release()
    }

    fun onWin() {
        mediaPlayer = MediaPlayer.create(context, R.raw.on_win)
        mediaPlayer.start()
    }

    fun onLosing() {
        mediaPlayer = MediaPlayer.create(context, R.raw.on_losing)
        mediaPlayer.start()

    }
}