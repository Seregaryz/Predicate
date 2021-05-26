package com.example.predicate.fragment.speech

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable

class SpeechViewModel: ViewModel() {

    private val speechRelay = BehaviorRelay.create<String>()

    val speech: Observable<String> = speechRelay.hide()

    fun catchSpeechResult(result: String) {
        speechRelay.accept(result)
    }
}