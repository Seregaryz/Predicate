package com.example.predicate.ui.statistic

import androidx.hilt.lifecycle.ViewModelInject
import com.example.predicate.ui.base.BaseViewModel
import com.example.predicate.interactor.SpeechInteractor
import com.example.predicate.model.mistake.MistakesItem
import com.example.predicate.model.schedulers.SchedulersProvider
import com.example.predicate.system.ErrorHandler
import com.example.predicate.system.SingleEvent
import com.example.predicate.system.acceptSingleEvent
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class StatisticViewModel @ViewModelInject constructor(
    private val errorHandler: ErrorHandler,
    private val schedulers: SchedulersProvider,
    private val speechInteractor: SpeechInteractor
): BaseViewModel() {

    var currentList = mutableListOf<MistakesItem?>()

    private var disposable: Disposable? = null

    private val errorMessageRelay = BehaviorRelay.create<SingleEvent<String>>()
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    private val mistakesRelay = BehaviorRelay.create<List<MistakesItem?>>()

    val errorMessage: Observable<SingleEvent<String>> = errorMessageRelay.hide()
    val loading: Observable<Boolean> = loadingRelay.hide()
    val mistakes: Observable<List<MistakesItem?>> = mistakesRelay.hide()

    fun deleteMistake(item: MistakesItem?, position: Int) {
        disposable = speechInteractor.deleteMistake(item?.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingRelay.accept(true) }
            .doFinally {
                loadingRelay.accept(false)
                currentList.removeAt(position)
                mistakesRelay.accept(currentList)
            }
            .doOnComplete {
            }
            .subscribe (
                {
                }, { e ->
                    errorHandler.proceed(e) {
                        errorMessageRelay.acceptSingleEvent(it)
                    }
                }
            )
    }

}