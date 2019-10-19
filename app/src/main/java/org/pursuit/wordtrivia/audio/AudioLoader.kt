package org.pursuit.wordtrivia.audio

import android.content.Context
import android.media.MediaPlayer
import org.pursuit.wordtrivia.R

class AudioLoader(
    private val context: Context,
    private var mediaPlayer: MediaPlayer


) {
    private var mediaPlayer2: MediaPlayer = MediaPlayer()
    private var isPlaying: Boolean = false
    fun backGroundMusic() {
        if (!isPlaying) {
            mediaPlayer2 = MediaPlayer.create(context, R.raw.gameplay_music)
            mediaPlayer2.isLooping=true
            mediaPlayer2.start()
            isPlaying = true


        }else
        mediaPlayer2.start()

    }

    fun onSplashScreen() {
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
        mediaPlayer2.release()
    }

    fun onWin() {
        if (isPlaying) {
            mediaPlayer2.stop()
            mediaPlayer2.release()
            isPlaying = false
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.on_win)
        mediaPlayer.start()
    }

    fun onLosing() {
        if (isPlaying) {
            mediaPlayer2.stop()
            mediaPlayer.release()
            isPlaying = false
            mediaPlayer = MediaPlayer.create(context, R.raw.fail_fare)
            mediaPlayer.start()

        }
    }


        }

