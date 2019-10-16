package org.pursuit.wordtrivia.presenter

import org.pursuit.wordtrivia.contract.MainContract

class TheGamePresenter(private val currentWord: String?, private val viewRef: MainContract.View) :

    MainContract.GamePresenter {
    override fun letterPressed(word: String?) {
        if (incorrectGuess != 0) {
            viewRef.revealHiddenLetter(word, guessWord(word!!),incorrectGuess)
        } else {
            viewRef.gameOver()
        }

    }

    override fun userWordInput(word: String?) {
    }

    override fun startGame() {
    }

    private var guessedWord: Boolean = false
    private var blankArray = ArrayList<String>()
    private var incorrectGuess: Int = 6



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


