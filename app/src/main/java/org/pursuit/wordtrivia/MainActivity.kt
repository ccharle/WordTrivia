package org.pursuit.wordtrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.pursuit.wordtrivia.network.WordClient
import org.pursuit.wordtrivia.presenter.TheGamePresenter
import org.pursuit.wordtrivia.presenter.WordNetworkPresenter

private const val TAG = "Response"

class MainActivity : AppCompatActivity(), MainContract.View {
    override fun showBlanks(word: String?) {
        txtvw_guessword.text = word
    }

    private val wordClient by lazy {
        WordClient.create()

    }

    private lateinit var netWorkPresenterRef: WordNetworkPresenter
    private lateinit var gamePresenterRef:TheGamePresenter

    override fun showWord(word: String?) {
        gamePresenterRef = TheGamePresenter(word,this)
        gamePresenterRef.startGame()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        netWorkPresenterRef = WordNetworkPresenter(wordClient, this)
        netWorkPresenterRef.getWordList()


    }


}

