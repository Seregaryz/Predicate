package com.example.predicate.fragment.sign_up

import androidx.hilt.lifecycle.ViewModelInject
import com.example.predicate.fragment.base.BaseViewModel
import com.example.predicate.interactor.UserInteractor
import com.example.predicate.model.schedulers.SchedulersProvider
import com.example.predicate.model.user.UserAccount
import com.example.predicate.model.user.UserSignUpItem
import com.example.predicate.system.ErrorHandler
import com.example.predicate.system.SingleEvent
import com.example.predicate.system.acceptSingleEvent
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignUpViewModel @ViewModelInject constructor(
    private val errorHandler: ErrorHandler,
    private val schedulers: SchedulersProvider,
    private val userInteractor: UserInteractor
) : BaseViewModel() {

    val currentUserData = UserSignUpItem()

    private var disposable: Disposable? = null

    private val errorMessageRelay = BehaviorRelay.create<SingleEvent<String>>()
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    private val successSignUpRelay = BehaviorRelay.create<UserAccount>()

    val errorMessage: Observable<SingleEvent<String>> = errorMessageRelay.hide()
    val loading: Observable<Boolean> = loadingRelay.hide()
    val successSignUp: Observable<UserAccount> = successSignUpRelay.hide()

    fun createAccount() {
        disposable = userInteractor.createAccount(currentUserData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingRelay.accept(true) }
            .doFinally { loadingRelay.accept(false) }
            .subscribe (
                {
                    successSignUpRelay.accept(it)
                    userInteractor.setAccount(it)
                }, { e ->
                    errorHandler.proceed(e) {
                        errorMessageRelay.acceptSingleEvent(it)
                    }
                }
            )
    }
}