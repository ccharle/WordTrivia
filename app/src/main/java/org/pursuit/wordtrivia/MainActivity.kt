package org.pursuit.wordtrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.pursuit.wordtrivia.network.WordClient
import org.pursuit.wordtrivia.presenter.WordPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "Response"

class MainActivity : AppCompatActivity(), MainContract.View {
    private val wordClient by lazy {
        WordClient.create()

    }

    private lateinit var pesenterRef: WordPresenter

    override fun showWord(word: String?) {
        txtvw_guessword.text = word
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pesenterRef = WordPresenter(this, wordClient, this)
        pesenterRef.getWordList()


    }


}

