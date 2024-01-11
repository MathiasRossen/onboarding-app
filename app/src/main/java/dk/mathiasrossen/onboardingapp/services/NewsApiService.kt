package dk.mathiasrossen.onboardingapp.services

import dk.mathiasrossen.onboardingapp.models.NewsSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines/sources")
    fun getSources(@Query("language") language: String = "en"): Single<List<NewsSource>>
}

val client = OkHttpClient.Builder()
    .addInterceptor(AuthorizationInterceptor())
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://newsapi.org")
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .client(client)
    .build()

val newsApiService = retrofit.create(NewsApiService::class.java)