package com.example.yandextranslator.model

import io.reactivex.rxkotlin.toObservable
import io.realm.Realm
import javax.inject.Inject

class RealmTranslationRepository @Inject constructor(private val realm: Realm?) {

    fun saveTranslation(translationRealmModel: TranslationRealmModel){
        realm?.executeTransactionAsync {realm->
            realm.copyToRealmOrUpdate(translationRealmModel)
        }
    }

    fun loadAllAsync(){
        realm!!.where(TranslationRealmModel::class.java)
                .findAllAsync()
                .toObservable()
               // .subscribe {it.}
    }

    fun closeRealm(){
        if (realm != null && !realm.isClosed) {
            realm.close()
        }
    }
}