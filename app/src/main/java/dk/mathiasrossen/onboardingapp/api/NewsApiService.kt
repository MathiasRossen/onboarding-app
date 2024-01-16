package dk.mathiasrossen.onboardingapp.api

import dk.mathiasrossen.onboardingapp.api.response_models.ArticlesResponse
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines/sources")
    fun getSources(@Query("language") language: String = DEFAULT_LANGUAGE): Single<NewsSourcesResponse>

    @GET("v2/everything")
    fun getArticlesFromSource(
        @Query("sources") source: String,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Single<ArticlesResponse>

    companion object {
        private const val DEFAULT_LANGUAGE = "en"
    }
}
