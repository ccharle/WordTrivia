package org.pursuit.wordtrivia.presenter

import android.util.Log
import org.pursuit.wordtrivia.R
import org.pursuit.wordtrivia.audio.AudioLoader
import org.pursuit.wordtrivia.contract.MainContract

class TheGamePresenter(private val currentWord: String?, private val viewRef: MainContract.View) :

    MainContract.GamePresenter {
    override fun onGameWon() {

    }

    override fun onRefreshGame() {
        incorrectGuess = 6
        lettersGuessedArray.clear()
        incorrectGuessCount = 0
        duplicateCounter = 0


    }


    private var lettersGuessedArray = arrayListOf<String>()
    private var guessedWord: Boolean = false
    private var blankArray = ArrayList<String>()
    private var incorrectGuess: Int = 6
    private var incorrectGuessCount = 0
    private var correctGuessCount = 0
    private var duplicateCounter = 0

    override fun letterPressed(word: String?) {
        if (incorrectGuess != 0) {
            viewRef.revealHiddenLetter(word, guessWord(word!!), incorrectGuess, lettersGuessedArray)
            if (currentWord != null) {
                if (correctGuessCount == currentWord.length) {
                    viewRef.gameWon()
                }

            }
        }
        if (incorrectGuess == 0) {
            viewRef.gameOver()
        }
    }


    override fun userWordInput(word: String?) {
    }

    override fun startGame() {
    }


    private fun guessWord(input: String): Boolean {
        //returns true if the word being guessed contains any character that the person entered

        return if (currentWord!!.contains(input.toLowerCase()) || currentWord.contains(input)) {
            if (!blankArray.contains(input)) {
                //if the current index in the given word is equal to the character given by the user
                //the correct guess count is incremented
                //this is implemented so that the program can check the counter to see if the user has won
                //this only increments once and it accounts for if the user guess a letter that would reveal multiple lines
                for (i in currentWord.indices) {
                    if (currentWord[i] == input.toLowerCase().single() || currentWord[i] == input.single()) {

                        correctGuessCount++
                        // current guesss goes up for each instance of the letter


                    }
                }
                blankArray.add(duplicateCounter, input)
                //duplicate counter goes up by one and i used to account for multiple instances of the word affecting the
                //ability to check correct guess to its length
                duplicateCounter++
                viewRef.correctSound()
            }
            true
        } else {
            viewRef.showUserProgress(displayFall())
            viewRef.incorrectSound()
            lettersGuessedArray.add(incorrectGuessCount, input)
            incorrectGuessCount++
            incorrectGuess--
            false
        }
    }

    private fun displayFall(): Int {
        var fallFrame = 0
        when (incorrectGuess) {
            6 -> fallFrame = R.drawable.fall_6
            5 -> fallFrame = R.drawable.fall_5
            4 -> fallFrame = R.drawable.fall_4
            3 -> fallFrame = R.drawable.fall_3
            2 -> fallFrame = R.drawable.fall_2
            1 -> fallFrame = R.drawable.fall_1

        }
        return fallFrame

    }


}


