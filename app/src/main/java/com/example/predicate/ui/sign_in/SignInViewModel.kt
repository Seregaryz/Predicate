package com.example.predicate.ui.sign_in

import androidx.hilt.lifecycle.ViewModelInject
import com.example.predicate.ui.base.BaseViewModel
import com.example.predicate.interactor.UserInteractor
import com.example.predicate.model.schedulers.SchedulersProvider
import com.example.predicate.model.user.UserAccount
import com.example.predicate.system.ErrorHandler
import com.example.predicate.system.SingleEvent
import com.example.predicate.system.acceptSingleEvent
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignInViewModel @ViewModelInject constructor(
    private val errorHandler: ErrorHandler,
    private val schedulers: SchedulersProvider,
    private val userInteractor: UserInteractor
) : BaseViewModel() {

    private var disposable: Disposable? = null

    var currentLogin = ""
    var currentPassword = ""

    private val errorMessageRelay = BehaviorRelay.create<SingleEvent<String>>()
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    private val successSignInRelay = BehaviorRelay.create<UserAccount>()

    val errorMessage: Observable<SingleEvent<String>> = errorMessageRelay.hide()
    val loading: Observable<Boolean> = loadingRelay.hide()
    val successSignUp: Observable<UserAccount> = successSignInRelay.hide()

    fun signIn() {
        disposable = userInteractor.signIn(currentLogin, currentPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingRelay.accept(true) }
            .doFinally { loadingRelay.accept(false) }
            .subscribe (
                {
                    successSignInRelay.accept(it)
                    userInteractor.setAccount(it)
                }, { e ->
                    errorHandler.proceed(e) {
                        errorMessageRelay.acceptSingleEvent(it)
                    }
                }
            )
    }

    fun validateData(): Boolean =
        currentLogin != "" && currentPassword != ""
}