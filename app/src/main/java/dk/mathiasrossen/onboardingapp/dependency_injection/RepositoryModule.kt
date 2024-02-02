package dk.mathiasrossen.onboardingapp.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.data.AppDatabase
import dk.mathiasrossen.onboardingapp.data.article.ArticleRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideArticleRepository(newsApiService: NewsApiService, db: AppDatabase): ArticleRepository =
        ArticleRepository(newsApiService, db.articleDao())
}