package dk.mathiasrossen.onboardingapp.services

import dk.mathiasrossen.onboardingapp.models.NewsSource
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines/sources")
    fun getSources(@Query("language") language: String = "en"): Call<List<NewsSource>>
}

val client = OkHttpClient.Builder()
    .addInterceptor(CoreInterceptor())

val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org").addConverterFactory(
    MoshiConverterFactory.create()
).build()

val newsApiService = retrofit.create(NewsApiService::class.java)