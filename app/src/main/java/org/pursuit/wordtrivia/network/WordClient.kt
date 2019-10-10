package org.pursuit.wordtrivia.network

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface WordClient {
    @GET("words")
    fun getAllWords(): Call<String>

    companion object {
       private const val BASE_URL: String = "http://app.linkedin-reach.io/"

        fun create(): WordClient {

            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            val client: OkHttpClient = builder.build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()



            return retrofit.create(WordClient::class.java)

        }

    }
}



