package org.pursuit.wordtrivia.view

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.pursuit.wordtrivia.R
import org.pursuit.wordtrivia.adapter.AlphabetAdapter
import org.pursuit.wordtrivia.audio.AudioLoader
import org.pursuit.wordtrivia.contract.MainContract
import org.pursuit.wordtrivia.network.WordClient
import org.pursuit.wordtrivia.presenter.TheGamePresenter
import org.pursuit.wordtrivia.presenter.WordNetworkPresenter


private const val TAG = "Response"
private const val remainingGuessesSentence = " Guesses remaining."

class MainActivity : AppCompatActivity(), MainContract.View {


    private var displayWordsArray = arrayListOf<TextView>()
    private var blanksArray = arrayListOf<TextView>()
    private var wrongGuessTally = ""
    private var buttonPressed: String? = null
    private lateinit var netWorkPresenterRef: WordNetworkPresenter
    private lateinit var gamePresenterRef: TheGamePresenter
    private lateinit var alphabetAdapter: AlphabetAdapter
    private val audioLoader = AudioLoader(this, MediaPlayer())


    private val wordClient by lazy {
        WordClient.create()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSetup()


    }

    override fun onResume() {
        super.onResume()
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
    }

    override fun incorrectSound() {
        audioLoader.incorrectSound()

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
        audioLoader.releaseMediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        audioLoader.releaseMediaPlayer()
    }

}


