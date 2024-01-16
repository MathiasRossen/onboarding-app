package dk.mathiasrossen.onboardingapp.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {
    @Provides
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}