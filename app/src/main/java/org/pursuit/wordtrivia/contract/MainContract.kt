package org.pursuit.wordtrivia.contract

interface MainContract {
    interface View {
        fun onLetterPressed()
        fun randomHiddenWord(word: String?)
        fun showBlanks(word: String?)
        fun revealHiddenLetter(
            letter: String?,
            boolean: Boolean,
            score: Int,
            guessedLettersArray: ArrayList<String>
        )

        fun gameOver()
        fun gameWon()
        fun showUserProgress(img :Int)
        fun correctSound()
        fun incorrectSound()
        fun showLoading()
        fun hideLoading()
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
        fun onRefreshGame()
        fun onGameWon()
    }


    interface Model {

    }
}
