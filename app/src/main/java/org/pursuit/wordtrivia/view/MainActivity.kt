package org.pursuit.wordtrivia.view

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.media.Image
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.pursuit.wordtrivia.R
import org.pursuit.wordtrivia.adapter.AlphabetAdapter
import org.pursuit.wordtrivia.audio.AudioLoader
import org.pursuit.wordtrivia.contract.MainContract
import org.pursuit.wordtrivia.network.WordClient
import org.pursuit.wordtrivia.presenter.TheGamePresenter
import org.pursuit.wordtrivia.presenter.WordNetworkPresenter
import java.util.*
import kotlin.collections.ArrayList


private const val remainingGuessesSentence = " Guesses remaining."

class MainActivity : AppCompatActivity(), MainContract.View {
    override fun hideLoading() {
       progress_bar.visibility = View.GONE

    }

    private var displayWordsArray = arrayListOf<TextView>()
    private var blanksArray = arrayListOf<TextView>()
    private var wrongGuessTally = ""
    private var buttonPressed: String? = null
    private lateinit var netWorkPresenterRef: WordNetworkPresenter
    private lateinit var gamePresenterRef: TheGamePresenter
    private lateinit var alphabetAdapter: AlphabetAdapter
    private val audioLoader = AudioLoader(this, MediaPlayer())
    private var animations: AnimationDrawable? = null
    private val wordClient by lazy {
        WordClient.create()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animations = gridvw_letters.background as? AnimationDrawable
        backGroundAnimations()
        gameSetup()


    }

    override fun showLoading() {
        progress_bar.isIndeterminate = true
        progress_bar.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()
        if (animations != null && !animations!!.isRunning()) {
            animations!!.start()
        }
        audioLoader.backGroundMusic()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun revealHiddenLetter(
        letter: String?,
        boolean: Boolean,
        score: Int,
        guessedLettersArray: ArrayList<String>
    ) {

        for (i in guessedLettersArray.indices) {
            if (!wrongGuessTally.contains(guessedLettersArray[i])) {
                wrongGuessTally += guessedLettersArray[i] + "\n"
            }
        }
        txtvw_incorrectguesses.text = wrongGuessTally + " "
        txtvw_incorrectguesses
        txvw_score.text = score.toString() + remainingGuessesSentence
        if (boolean) {
            for (i in displayWordsArray.indices) {
                if (displayWordsArray[i].text.toString().contains(letter!!.single().toLowerCase())) {
                    displayWordsArray[i].setTextColor(Color.BLACK)

                }

            }
        }
    }


    override fun onLetterPressed() {

        gamePresenterRef.letterPressed(buttonPressed!!)
    }


    override fun showBlanks(word: String?) {
    }


    override fun randomHiddenWord(word: String?) {
        txvw_score.text = 6.toString() + remainingGuessesSentence
        setArray(word)
        gamePresenterRef = TheGamePresenter(word, this)
        gridvw_letters.setOnItemClickListener { parent, view, position, id ->

            val buttonPressed: Button =
                gridvw_letters.getChildAt(position) as Button
            val letter = buttonPressed.text
            gamePresenterRef.letterPressed(letter.toString())

        }
        gamePresenterRef.startGame()

    }

    private fun setArray(word: String?) {
        if (displayWordsArray.isNotEmpty()) {
            displayWordsArray.clear()
            linear_layout1.removeAllViews()
            blanksArray.clear()
            linear_layout2.removeAllViews()
        }
        if (word != null) {
            for (index in word.indices) {
                displayWordsArray.add(index, TextView(this))
                displayWordsArray[index].text = word!![index] + " "
                displayWordsArray[index].textSize = 32f
                displayWordsArray[index].setTextColor(Color.WHITE)
                linear_layout1.addView(displayWordsArray[index])

            }
            for (index in word.indices) {
                blanksArray.add(index, TextView(this))
                blanksArray[index].text = "_" + " "
                blanksArray[index].textSize = 32f
                blanksArray[index].setTextColor(Color.BLACK)

                linear_layout2.addView(blanksArray[index])

            }

        }

    }

    override fun gameOver() {
        gridvw_letters.isEnabled = false
        gridvw_letters.isClickable = false
        txtvw_gameover.setTextColor(Color.BLACK)
        for (index in displayWordsArray.indices) {
            displayWordsArray[index].setTextColor(Color.BLACK)


        }

        audioLoader.onLosing()


    }

    override fun gameWon() {
        val snack =
            Snackbar.make(main_constraintlayout, "WINNER WINNER!", Snackbar.LENGTH_LONG)
        snack.show()
        audioLoader.onWin()
    }

    private fun gameSetup() {
        audioLoader.backGroundMusic()
        alphabetAdapter = AlphabetAdapter(this)
        txtvw_incorrectguesses.movementMethod = ScrollingMovementMethod()
        gridvw_letters.adapter = alphabetAdapter
        netWorkPresenterRef = WordNetworkPresenter(wordClient, this)
        netWorkPresenterRef.getWordList()
        bttn_newword.setOnClickListener {
            audioLoader.backGroundMusic()
            resetGame()

        }
        bttn_reveal.setOnClickListener {
            gameOver()

        }
        txtvw_incorrectguesses.movementMethod
    }

    override fun correctSound() {
        audioLoader.correctSound()
        showGreenLight()

        Handler().postDelayed({
            bttn_buzzer.setBackgroundResource(R.drawable.circular_button)
        }, 300)

    }

    override fun incorrectSound() {
        audioLoader.incorrectSound()
        showRedLight()

        Handler().postDelayed({
            bttn_buzzer.setBackgroundResource(R.drawable.circular_button)
        }, 300)

    }


    override fun showUserProgress(img: Int) {
        imgvw_userprogress.setBackgroundResource(img)
    }

    private fun resetGame() {
        gridvw_letters.isEnabled = true
        gridvw_letters.isClickable = true
        netWorkPresenterRef.requestRandomWord();gamePresenterRef.onRefreshGame()
        txtvw_incorrectguesses.text = ""
        wrongGuessTally = ""
        txtvw_gameover.setTextColor(Color.WHITE)
        imgvw_userprogress.setBackgroundResource(R.color.colorwhite)
    }

    override fun onStop() {
        super.onStop()
        audioLoader.onPause()
        if (animations != null && !animations!!.isRunning()) {
            animations!!.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioLoader.releaseMediaPlayer()
    }

    private fun backGroundAnimations() {
        animations?.setEnterFadeDuration(5000)

        animations?.setExitFadeDuration(2000)


    }

    private fun showGreenLight() {
        bttn_buzzer.setBackgroundResource(R.drawable.circular_button_correct)

    }

    private fun showRedLight() {
        bttn_buzzer.setBackgroundResource(R.drawable.circular_button_wrong)

    }
}


