package dk.mathiasrossen.onboardingapp.utils.errors

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorActionBus @Inject constructor() {
    private val errorAction = PublishSubject.create<Unit>()

    fun updateErrorAction() {
        errorAction.onNext(Unit)
    }

    fun listenErrorAction(): Observable<Unit> = errorAction
}
