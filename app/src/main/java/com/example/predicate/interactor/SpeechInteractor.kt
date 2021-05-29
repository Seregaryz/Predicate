package com.example.predicate.interactor

import com.example.predicate.data.network.Api
import com.example.predicate.model.mistake.MistakeResponce
import com.example.predicate.model.request_body.ArgumentRequestBody
import com.example.predicate.model.schedulers.SchedulersProvider
import com.example.predicate.system.SessionKeeper
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SpeechInteractor @Inject constructor(
    private val api: Api,
    private val schedulers: SchedulersProvider
) {
    fun sendArguments(currentText: String): Single<MistakeResponce> {
        val requestBody = ArgumentRequestBody(currentText)
        return api.sendArgument(requestBody)
    }

    fun deleteMistake(id: Int?): Completable {
        return api.deleteMistake(id)
    }

}