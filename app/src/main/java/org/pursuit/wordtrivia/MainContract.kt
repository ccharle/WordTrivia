package org.pursuit.wordtrivia

import java.util.*
import kotlin.collections.HashMap

interface MainContract {
    interface View {
        fun showWord(word: String?)
    }

    interface Presenter {

        fun getWordList()
        fun netWorkCallFinished(onNetworkListener: OnNetworkCallListener)

        interface OnNetworkCallListener {
            fun onNetworkCallFinished(dictionary: HashMap<String, String>?)
        }


    }

    interface Model {

    }
}
