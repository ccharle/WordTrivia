package org.pursuit.wordtrivia.audio

import android.content.Context
import android.media.MediaPlayer
import org.pursuit.wordtrivia.R

class AudioLoader(
    private val context: Context,
    private var mediaPlayer: MediaPlayer
) {

    fun correctSound() {

        mediaPlayer = MediaPlayer.create(context, R.raw.correct_sound)
        mediaPlayer.start()


    }

    fun incorrectSound() {

        mediaPlayer = MediaPlayer.create(context, R.raw.wrong_answer)
        mediaPlayer.start()


    }

    fun releaseMediaPlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }


}