package org.pursuit.wordtrivia.presenter

import org.pursuit.wordtrivia.MainContract

class TheGamePresenter(private val currentWord: String?, private val viewRef: MainContract.View) :

    MainContract.GamePresenter {
    override fun letterPressed(word: String?) {
        if (incorrectGuess != 0) {
            viewRef.revealHiddenLetter(word, guessWord(word!!))
        } else {
            viewRef.gameOver()
        }

    }

    override fun userWordInput(word: String?) {
    }

    override fun startGame() {
        setBlanks()
    }

    private var guessedWord: Boolean = false
    private var blankArray = ArrayList<String>()
    private var blanks: String? = ""
    private var incorrectGuess: Int = 6
    private val hiddenPhrase: ArrayList<String>? = null
    private val arrayOfBlanks: ArrayList<String>? = null


    private fun setBlanks() {
        if (currentWord != null) {
            for (i in currentWord) {
                blankArray.add(i.toString())
            }
        }
        viewRef.showBlanks(blankArray.toString())


    }

    private fun guessWord(input: String): Boolean {
        return if (currentWord!!.contains(input.toLowerCase()) || currentWord.contains(input)) {

            true
        } else {
            incorrectGuess--
            false
        }
    }


}


