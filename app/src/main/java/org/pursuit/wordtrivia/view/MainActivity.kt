package org.pursuit.wordtrivia.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.pursuit.wordtrivia.contract.MainContract
import org.pursuit.wordtrivia.R
import org.pursuit.wordtrivia.adapter.AlphabetAdapter
import org.pursuit.wordtrivia.network.WordClient
import org.pursuit.wordtrivia.presenter.TheGamePresenter
import org.pursuit.wordtrivia.presenter.WordNetworkPresenter
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "Response"

class MainActivity : AppCompatActivity(), MainContract.View {
    override fun gameOver() {

    }


    private var textViewArray = arrayListOf<TextView>()
    private var textViewArray2 = arrayListOf<TextView>()

    private var buttonPressed: String? = null
    private lateinit var netWorkPresenterRef: WordNetworkPresenter
    private lateinit var gamePresenterRef: TheGamePresenter
    private lateinit var alphabetAdapter: AlphabetAdapter
    private val wordClient by lazy {
        WordClient.create()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alphabetAdapter = AlphabetAdapter(this)
        gridvw_letters.adapter = alphabetAdapter
        netWorkPresenterRef = WordNetworkPresenter(wordClient, this)
        netWorkPresenterRef.getWordList()
        bttn_newword.setOnClickListener { netWorkPresenterRef.requestRandomWord() }

    }

    override fun revealHiddenLetter(letter: String?, boolean: Boolean, score: Int) {
        txvw_score.text = score.toString()
        if (boolean) {
            for (i in textViewArray.indices) {
                if (textViewArray[i].text.toString().contains(letter!!.single().toLowerCase())) {
                    Toast.makeText(
                        this,
                        "YES" + textViewArray[i].text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    textViewArray[i].setTextColor(Color.BLACK)
                }
            }
        } else {

            Toast.makeText(
                this,
                "NO" + letter + textViewArray[2].text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onLetterPressed() {

        gamePresenterRef.letterPressed(buttonPressed!!)
    }


    override fun showBlanks(word: String?) {
    }


    override fun randomHiddenWord(word: String?) {
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
        if (textViewArray.isNotEmpty()) {
            textViewArray.clear()
            linear_layout1.removeAllViews()
            textViewArray2.clear()
            linear_layout2.removeAllViews()
        }
        if (word != null) {
            for (index in word.indices) {
                textViewArray.add(index, TextView(this))
                textViewArray[index].text = word!![index] + " "
                textViewArray[index].textSize = 32f
                textViewArray[index].setTextColor(Color.WHITE)
                linear_layout1.addView(textViewArray[index])

            }
            for (index in word.indices) {
                textViewArray2.add(index, TextView(this))
                textViewArray2[index].text = "_" + " "
                textViewArray2[index].textSize = 32f
                textViewArray2[index].setTextColor(Color.BLACK)

                linear_layout2.addView(textViewArray2[index])

            }

        }
        Toast.makeText(
            this,
            "TextView Array " + textViewArray.size.toString() + " " + word,
            Toast.LENGTH_SHORT
        ).show()

    }

}


