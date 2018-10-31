package com.example.yandextranslator.model

import android.annotation.SuppressLint
import android.util.Log
import android.util.Size
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import io.reactivex.rxkotlin.toObservable
import io.realm.Realm
import io.realm.RealmResults
import java.util.*
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
    }

    fun loadRandomWord(): TranslationRealmModel? = realm?.where(TranslationRealmModel::class.java)
                ?.findAllAsync()
                ?.get(getRandomNumber(getTranslationWordCount()?.toInt()!!))


    private fun getTranslationWordCount() = realm?.where(TranslationRealmModel::class.java)?.count()

    fun closeRealm(){
        if (realm != null && !realm.isClosed) {
            realm.close()
        }
    }

    companion object {
        @JvmStatic
        fun getRandomNumber(size: Int) = Random().nextInt(size)
    }
}