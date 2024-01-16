package dk.mathiasrossen.onboardingapp.api

import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines/sources")
    fun getSources(@Query("language") language: String = "en"): Single<NewsSourcesResponse>
}
