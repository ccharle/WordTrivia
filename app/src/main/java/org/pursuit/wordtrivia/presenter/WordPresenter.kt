package org.pursuit.wordtrivia.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import org.pursuit.wordtrivia.MainContract
import org.pursuit.wordtrivia.network.WordClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG: String = "dictionaryItems"

class WordPresenter(

    private val context: Context,
    private val client: WordClient,
    private val viewRef: MainContract.View
) :
    MainContract.Presenter, MainContract.Presenter.OnNetworkCallListener {

    override fun onNetworkCallFinished(dictionary: HashMap<String, String>?) {
        viewRef.showWord(dictionary?.getOrDefault("abs", "Empty"))
    }


    override fun getWordList() {
        getWords()
    }

    override fun netWorkCallFinished(onNetworkListener: MainContract.Presenter.OnNetworkCallListener) {
    }


    private fun getWords() {

        client.getAllWords().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val wordDictionary = HashMap<String, String>()

                if (response.body() != null) {

                    val wordResponse: String? = response.body()
                    if (wordResponse != null) {
//                        for (it in wordResponse) {
//                            Log.d(TAG,it.toString())
//                            if (it.toString() == "\n") {
//                                Log.d(TAG, "Space$it")
                        val wordList: List<String> = response.body()!!.lines().toList()
                        wordList.forEach { wordDictionary[it] = it }
                    }

                }
                onNetworkCallFinished( wordDictionary)
            }

        })


    }


}
