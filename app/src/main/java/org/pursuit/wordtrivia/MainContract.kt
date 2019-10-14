package org.pursuit.wordtrivia

import java.util.*
import kotlin.collections.HashMap

interface MainContract {
    interface View {
        fun showHiddenWord(word: String?)
        fun showBlanks(word: String?)
    }


        interface NetworkPresenter {

            fun getWordList()
            fun netWorkCallFinished(onNetworkListener: OnNetworkCallListener)

            interface OnNetworkCallListener {
                fun onNetworkCallFinished(dictionary: HashMap<Int, String>?)
            }


        }

        interface GamePresenter {

            fun startGame()
            fun userWordInput(word: String?)
        }



    interface Model {

    }
}
