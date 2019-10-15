package org.pursuit.wordtrivia

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.button_layout.*
import org.pursuit.wordtrivia.adapter.AlphabetAdapter
import org.pursuit.wordtrivia.network.WordClient
import org.pursuit.wordtrivia.presenter.TheGamePresenter
import org.pursuit.wordtrivia.presenter.WordNetworkPresenter

private const val TAG = "Response"

class MainActivity : AppCompatActivity(), MainContract.View {


    private lateinit var textViewArray: Array<TextView?>

    private var buttonPressed: String? = null
    private lateinit var netWorkPresenterRef: WordNetworkPresenter
    private lateinit var gamePresenterRef: TheGamePresenter
    private lateinit var alphabetAdapter: AlphabetAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alphabetAdapter = AlphabetAdapter(this)
        gridvw_letters.adapter = alphabetAdapter
        netWorkPresenterRef = WordNetworkPresenter(wordClient, this)
        netWorkPresenterRef.getWordList()


    }

    override fun revealHiddenLetter(letter: String?, boolean: Boolean) {
        if (boolean) {
            for (i in textViewArray.indices) {
                if (textViewArray[i]?.text.toString().contains(letter!!.single().toLowerCase())) {
                    Toast.makeText(
                        this,
                        "YES" + textViewArray[i]?.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    textViewArray[i]?.setTextColor(Color.BLACK)
                }
            }
        } else {

            Toast.makeText(this, "NO" +letter + textViewArray[2]?.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    override fun onLetterPressed() {

        gamePresenterRef.letterPressed(buttonPressed!!)
    }


    override fun showBlanks(word: String?) {
        //txtvw_guessword.text = word
    }

    private val wordClient by lazy {
        WordClient.create()

    }

    override fun showHiddenWord(word: String?) {
        textViewArray = arrayOfNulls(word?.length!!)
        for (i in textViewArray.indices) {
            textViewArray[i] = TextView(this)
            textViewArray[i]?.text = word[i] + " "
            textViewArray[i]?.textSize = 32f
            textViewArray[i]?.setTextColor(Color.WHITE)
            linear_layout.addView(textViewArray[i])


        }

        gamePresenterRef = TheGamePresenter(word, this)
        gridvw_letters.setOnItemClickListener { parent, view, position, id ->

            val buttonPressed: Button =
                gridvw_letters.getChildAt(position) as Button
            val letter = buttonPressed.text
            gamePresenterRef.letterPressed(letter.toString())

        }
        gamePresenterRef.startGame()

    }


    fun letterPressed(view: View) {

        buttonPressed = view.toString()
        //val singleCharacter = pressedLetter[0]
        view.isEnabled = false
        view.setBackgroundResource(R.drawable.letter_down)
    }

}

