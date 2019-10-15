package org.pursuit.wordtrivia

import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

interface MainContract {
    interface View {
        fun onLetterPressed()
        fun showHiddenWord(word: String?)
        fun showBlanks(word: String?)
        fun revealHiddenLetter(letter:String?,boolean: Boolean)
        fun gameOver()
    }


    interface NetworkPresenter {

        fun getWordList()
        fun netWorkCallFinished(onNetworkListener: OnNetworkCallListener)

        interface OnNetworkCallListener {
            fun onNetworkCallFinished(dictionary: HashMap<Int, String>?)
        }


    }

    interface GamePresenter {
        fun letterPressed(word:String?)
        fun startGame()
        fun userWordInput(word: String?)

    }


    interface Model {

    }
}
