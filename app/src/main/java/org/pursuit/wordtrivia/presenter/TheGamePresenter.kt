package org.pursuit.wordtrivia.presenter

import android.util.Log
import org.pursuit.wordtrivia.contract.MainContract

class TheGamePresenter(private val currentWord: String?, private val viewRef: MainContract.View) :

    MainContract.GamePresenter {
    override fun onRefreshGame() {
        incorrectGuess = 6
        lettersGuessedArray.clear()
        guessCount = 0
    }


    private var lettersGuessedArray = arrayListOf<String>()
    private var guessedWord: Boolean = false
    private var blankArray = ArrayList<String>()
    private var incorrectGuess: Int = 6
    private var guessCount = 0


    override fun letterPressed(word: String?) {
        if (incorrectGuess != 0) {
            viewRef.revealHiddenLetter(word, guessWord(word!!), incorrectGuess, lettersGuessedArray)
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
                lettersGuessedArray.add(guessCount, input)
                guessCount++
            incorrectGuess--
            false
        }
    }


}


