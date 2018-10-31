package com.example.yandextranslator.model

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import io.reactivex.rxkotlin.toObservable
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class RealmTranslationRepository @Inject constructor(private val realm: Realm?) {

    fun saveTranslation(translationRealmModel: TranslationRealmModel){
        realm?.executeTransactionAsync {realm->
            realm.copyToRealmOrUpdate(translationRealmModel)
        }
    }

    fun loadAllAsync(): Observable<ArrayList<TranslationRealmModel>>? {
        return realm!!.where(TranslationRealmModel::class.java)
                .findAllAsync()
                .toObservable()
                .collect({ arrayListOf<TranslationRealmModel>()}, {t1, t2 -> t1.add(t2)})
                .toObservable()
                .doOnNext{Log.d("myLogs", "$it")}


    }

    fun closeRealm(){
        if (realm != null && !realm.isClosed) {
            realm.close()
        }
    }
}