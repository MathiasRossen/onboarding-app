package dk.mathiasrossen.onboardingapp.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.mathiasrossen.onboardingapp.data.article.ArticleRepository
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideArticlesUseCase(articlesRepository: ArticleRepository): ArticlesUseCase =
        ArticlesUseCase(articlesRepository)
}