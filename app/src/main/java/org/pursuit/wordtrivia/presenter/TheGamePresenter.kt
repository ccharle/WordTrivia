package org.pursuit.wordtrivia.presenter

import org.pursuit.wordtrivia.MainContract

class TheGamePresenter(private val currentWord: String?, private val viewRef: MainContract.View) :
    MainContract.GamePresenter {
    override fun startGame() {
        setBlanks()
    }

    private var guessedWord: Boolean = false
   private var blanks:String? = ""
    private val incorrectGuess: Int = 6
    private val hiddenPhrase: ArrayList<String>? = null
    private val arrayOfBlanks: ArrayList<String>? = null


    private fun setBlanks(){
        if (currentWord != null) {
            for (i in currentWord) {
               blanks+= " ____ "
            }
        }
        viewRef.showBlanks(blanks)



    }

}