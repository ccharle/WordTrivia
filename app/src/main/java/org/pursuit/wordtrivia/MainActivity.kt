package org.pursuit.wordtrivia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.pursuit.wordtrivia.network.WordClient
import org.pursuit.wordtrivia.presenter.TheGamePresenter
import org.pursuit.wordtrivia.presenter.WordNetworkPresenter

private const val TAG = "Response"

class MainActivity : AppCompatActivity(), MainContract.View {


    override fun showBlanks(word: String?) {
        //txtvw_guessword.text = word
    }

    private val wordClient by lazy {
        WordClient.create()

    }

    private lateinit var netWorkPresenterRef: WordNetworkPresenter
    private lateinit var gamePresenterRef: TheGamePresenter

    override fun showHiddenWord(word: String?) {
        val textViewArray = arrayOfNulls<TextView>(word?.length!!)
        for (i in textViewArray.indices) {
            textViewArray[i] = TextView(this)
            textViewArray[i]?.text = "__" + " "
            textViewArray[i]?.textSize = 32f
            linear_layout.addView(textViewArray[i])


        }

        gamePresenterRef = TheGamePresenter(word, this)
        gamePresenterRef.startGame()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        netWorkPresenterRef = WordNetworkPresenter(wordClient, this)
        netWorkPresenterRef.getWordList()
//        showSoftKeyboard(edttxt_wordpreview)
//        edttxt_wordpreview.setOnEditorActionListener { v, actionId, event ->
//            return@setOnEditorActionListener when (actionId) {
//                EditorInfo.IME_ACTION_DONE -> {
//                    if (edttxt_wordpreview.text != null) {
//                        gamePresenterRef.userWordInput(edttxt_wordpreview.text.toString())
//                        edttxt_wordpreview.text = null
//                    }
//                    true
//                }
//                else -> false
//            }
//        }


    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


}

