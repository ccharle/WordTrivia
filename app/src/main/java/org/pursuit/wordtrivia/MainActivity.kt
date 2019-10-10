package org.pursuit.wordtrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.pursuit.wordtrivia.network.WordClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "Response"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callTest()
    }

    val wordClient by lazy {
        WordClient.create()

    }

    private fun callTest() {

        wordClient.getAllWords().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                (Toast.makeText(this@MainActivity, "FAIL", Toast.LENGTH_LONG).show()
                        )
            }


            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null) {
                    Toast.makeText(this@MainActivity,"SUCCCESS!", Toast.LENGTH_LONG).show()
                    txtvw_guessword.text = response.body()
                    Log.d(TAG, response.body().toString())
                } else (Toast.makeText(this@MainActivity, "EMPTY", Toast.LENGTH_LONG).show()
                        )
            }

        })
    }
}
