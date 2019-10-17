package org.pursuit.wordtrivia.presenter

import org.pursuit.wordtrivia.contract.MainContract

class TheGamePresenter(private val currentWord: String?, private val viewRef: MainContract.View) :

    MainContract.GamePresenter {
    override fun onRefreshGame() {
        incorrectGuess = 6
        lettersGuessed.clear()
    }


    private var lettersGuessed = arrayListOf<String>()
    private var guessedWord: Boolean = false
    private var blankArray = ArrayList<String>()
    private var incorrectGuess: Int = 6


    override fun letterPressed(word: String?) {
        if (incorrectGuess != 0) {
            viewRef.revealHiddenLetter(word, guessWord(word!!), incorrectGuess, lettersGuessed)
        } else {
            viewRef.gameOver()
        }

    }

    override fun userWordInput(word: String?) {
    }

    override fun startGame() {
    }


    private fun guessWord(input: String): Boolean {
        return if (currentWord!!.contains(input.toLowerCase()) || currentWord.contains(input)) {

            true
        } else {
            for (i in lettersGuessed.indices) {
                lettersGuessed.add(i, input)
            }
            incorrectGuess--
            false
        }
    }


}


