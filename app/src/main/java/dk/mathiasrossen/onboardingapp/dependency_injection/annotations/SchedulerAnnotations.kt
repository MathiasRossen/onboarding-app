package dk.mathiasrossen.onboardingapp.dependency_injection.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UiScheduler

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoScheduler
