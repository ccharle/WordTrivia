package org.pursuit.wordtrivia.contract

interface MainContract {
    interface View {
        fun onLetterPressed()
        fun randomHiddenWord(word: String?)
        fun showBlanks(word: String?)
        fun revealHiddenLetter(letter: String?, boolean: Boolean, score: Int)
        fun gameOver()
    }


    interface NetworkPresenter {

        fun getWordList()
        fun requestRandomWord()
        fun netWorkCallFinished(onNetworkListener: OnNetworkCallListener)
        interface OnNetworkCallListener {
            fun onNetworkCallFinished(dictionary: HashMap<Int, String>?)
        }


    }

    interface GamePresenter {
        fun letterPressed(word: String?)
        fun startGame()
        fun userWordInput(word: String?)


    }


    interface Model {

    }
}
