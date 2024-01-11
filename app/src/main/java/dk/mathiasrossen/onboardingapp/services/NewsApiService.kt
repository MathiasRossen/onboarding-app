package dk.mathiasrossen.onboardingapp.services

import dk.mathiasrossen.onboardingapp.models.NewsSource
import retrofit2.Retrofit
import retrofit2.http.GET

interface NewsApiService {
    @GET("v2/top-headlines/sources")
    fun getSources(): List<NewsSource>
}

val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org").build()

val newsApiService = retrofit.create(NewsApiService::class.java)