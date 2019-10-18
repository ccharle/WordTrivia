package org.pursuit.wordtrivia.view

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.pursuit.wordtrivia.R
import org.pursuit.wordtrivia.audio.AudioLoader

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val splashDelay: Long = 1500
    private val audioLoader: AudioLoader = AudioLoader(this, MediaPlayer())

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, splashDelay)
        audioLoader.onSplashScreen()
    }

    public override fun onDestroy() {

        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(runnable)
            audioLoader.releaseMediaPlayer()
        }

        super.onDestroy()
    }
}

