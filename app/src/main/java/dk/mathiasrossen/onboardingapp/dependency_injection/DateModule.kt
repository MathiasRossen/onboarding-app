package dk.mathiasrossen.onboardingapp.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import dk.mathiasrossen.onboardingapp.utils.date.DateUtilsImpl

@Module
@InstallIn(SingletonComponent::class)
object DateModule {
    @Provides
    fun provideDateUtils(): DateUtils = DateUtilsImpl()
}