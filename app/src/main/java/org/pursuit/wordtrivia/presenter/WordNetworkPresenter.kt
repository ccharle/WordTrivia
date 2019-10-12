package org.pursuit.wordtrivia.presenter

import android.util.Log
import org.pursuit.wordtrivia.MainContract
import org.pursuit.wordtrivia.network.WordClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG: String = "dictionaryItems"
private const val NETWORK: String = "Response has"

class WordNetworkPresenter(
    private val client: WordClient,
    private val viewRef: MainContract.View
) :
    MainContract.NetworkPresenter, MainContract.NetworkPresenter.OnNetworkCallListener {

    override fun onNetworkCallFinished(dictionary: HashMap<String, String>?) {
        viewRef.showWord(dictionary?.getOrDefault("abs", "Empty"))
    }


    override fun getWordList() {
        getWords()
    }

    override fun netWorkCallFinished(onNetworkListener: MainContract.NetworkPresenter.OnNetworkCallListener) {
    }


    private fun getWords() {

        client.getAllWords().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(NETWORK, "Failed")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val wordDictionary = HashMap<String, String>()

                if (response.body() != null) {

                    val wordResponse: String? = response.body()
                    if (wordResponse != null) {

                        val wordList: List<String> = response.body()!!.lines().toList()
                        wordList.forEach { wordDictionary[it] = it }
                    }

                }
                onNetworkCallFinished(wordDictionary)
            }

        })


    }


}
