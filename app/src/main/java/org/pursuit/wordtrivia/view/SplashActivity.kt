package org.pursuit.wordtrivia.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.pursuit.wordtrivia.R

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val splashDelay: Long = 3000

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

    }

    public override fun onDestroy() {

        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(runnable)
        }

        super.onDestroy()
    }
}

