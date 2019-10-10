package org.pursuit.wordtrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.pursuit.wordtrivia.network.WordClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null) {
                    Toast.makeText(this@MainActivity, "SUCCESS!", Toast.LENGTH_LONG).show()
                } else (Toast.makeText(this@MainActivity, "FAIL", Toast.LENGTH_LONG).show()
                        )
            }

        })
    }
}
