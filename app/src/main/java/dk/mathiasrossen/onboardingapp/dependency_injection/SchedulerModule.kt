package dk.mathiasrossen.onboardingapp.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.IoScheduler
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {
    @UiScheduler
    @Provides
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @IoScheduler
    @Provides
    fun provideIoScheduler(): Scheduler = Schedulers.io()
}