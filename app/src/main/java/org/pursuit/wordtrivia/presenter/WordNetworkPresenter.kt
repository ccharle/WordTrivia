package org.pursuit.wordtrivia.presenter

import android.util.Log
import org.pursuit.wordtrivia.contract.MainContract
import org.pursuit.wordtrivia.network.WordClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.HashMap

private const val NETWORK: String = "Response has"

class WordNetworkPresenter(
    private val client: WordClient,
    private val viewRef: MainContract.View
) :
    MainContract.NetworkPresenter, MainContract.NetworkPresenter.OnNetworkCallListener {

    val wordDictionary = HashMap<Int, String>()

    override fun requestRandomWord() {
        viewRef.randomHiddenWord(wordDictionary?.getOrDefault(getRandomWord(), "Empty"))
    }



    override fun onNetworkCallFinished(dictionary: HashMap<Int, String>?) {
        viewRef.randomHiddenWord(dictionary?.getOrDefault(getRandomWord(), "Empty"))
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

                if (response.body() != null) {

                    val wordResponse: String? = response.body()
                    if (wordResponse != null) {

                        val wordList: List<String> = response.body()!!.lines().toList()
                        var count = 0

                        wordList.forEach { wordDictionary[count++] = it }
                    }

                }
                onNetworkCallFinished(wordDictionary)
            }

        })


    }

   private fun getRandomWord(): Int {
        return (0..wordDictionary.size).random()

    }


}
