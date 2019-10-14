package org.pursuit.wordtrivia.presenter

import org.pursuit.wordtrivia.MainContract

class TheGamePresenter(private val currentWord: String?, private val viewRef: MainContract.View) :
    MainContract.GamePresenter {


    override fun userWordInput(word: String?) {
        guessWord(word!!)
    }

    override fun startGame() {
        setBlanks()
    }

    private var guessedWord: Boolean = false
    private var blankArray = ArrayList<String>()
    private var blanks: String? = ""
    private val incorrectGuess: Int = 6
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

    private fun guessWord(input :String) {
        if (currentWord!!.contains(input)) {
            for (i in currentWord) {
                if (currentWord[i.toInt()].equals(input)) {
                    blankArray[i.toInt()] = input
                    viewRef.showBlanks(blankArray.toString())
                }
            }

        }


    }

}