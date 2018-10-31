package com.example.yandextranslator.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.example.yandextranslator.model.RealmTranslationRepository
import com.example.yandextranslator.model.TranslationRealmModel
import com.example.yandextranslator.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ListTranslationViewModel @Inject constructor(private val realmTranslationRepository: RealmTranslationRepository,
                                                   application: Application): BaseViewModel(application) {


    @SuppressLint("CheckResult")
    fun getTranslationList(): Observable<ArrayList<TranslationRealmModel>>? {
        return realmTranslationRepository
                .loadAllAsync()!!
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun setTransslate(){
        realmTranslationRepository.saveTranslation(TranslationRealmModel(
                id = UUID.randomUUID().toString(),
                inputText = "Олег",
                translationText = "Oleg",
                languages = "ru-en"))
    }
    override fun onCleared() {
        realmTranslationRepository.closeRealm()
        super.onCleared()
    }
}