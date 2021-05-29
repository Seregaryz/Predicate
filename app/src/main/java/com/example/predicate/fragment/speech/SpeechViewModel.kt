package com.example.predicate.fragment.speech

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.predicate.fragment.base.BaseViewModel
import com.example.predicate.interactor.SpeechInteractor
import com.example.predicate.interactor.UserInteractor
import com.example.predicate.model.mistake.MistakeResponce
import com.example.predicate.model.schedulers.SchedulersProvider
import com.example.predicate.system.ErrorHandler
import com.example.predicate.system.SingleEvent
import com.example.predicate.system.acceptSingleEvent
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class SpeechViewModel @ViewModelInject constructor(
    private val errorHandler: ErrorHandler,
    private val schedulers: SchedulersProvider,
    private val speechInteractor: SpeechInteractor
) : BaseViewModel() {

    var currentText = ""
    var programmaticallyMode = false
    var flag = true


    private var disposable: Disposable? = null

    private val errorMessageRelay = BehaviorRelay.create<SingleEvent<String>>()
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    private val mistakesRelay = BehaviorRelay.create<MistakeResponce>()

    val errorMessage: Observable<SingleEvent<String>> = errorMessageRelay.hide()
    val loading: Observable<Boolean> = loadingRelay.hide()
    val mistakes: Observable<MistakeResponce> = mistakesRelay.hide()

    fun sendArgument() {
        disposable = speechInteractor.sendArguments(currentText.toLowerCase(Locale.ROOT))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingRelay.accept(true) }
            .doFinally { loadingRelay.accept(false) }
            .subscribe (
                {
                    mistakesRelay.accept(it)
                }, { e ->
                    errorHandler.proceed(e) {
                        errorMessageRelay.acceptSingleEvent(it)
                    }
                }
            )
    }
}