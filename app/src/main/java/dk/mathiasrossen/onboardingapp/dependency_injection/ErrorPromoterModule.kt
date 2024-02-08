package dk.mathiasrossen.onboardingapp.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorPromoter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorPromoterModule {
    @Singleton
    @Provides
    fun provideErrorPromoter() = ErrorPromoter()
}
