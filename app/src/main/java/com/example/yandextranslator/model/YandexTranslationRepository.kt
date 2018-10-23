package com.example.yandextranslator.model

import android.annotation.SuppressLint
import android.util.Log
import com.example.yandextranslator.net.YandexApiClient
import com.example.yandextranslator.net.YandexApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class YandexTranslationRepository @Inject constructor(private val yandexApiService: YandexApiService): YandexApiClient{

    override fun getTranslate(apiKey: String, text: String, language: String): Observable<String> {
        return yandexApiService.getTranslate(apiKey, text, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {it.text[0]}
    }

    @SuppressLint("CheckResult")
    override fun getLanguages(apiKey: String, ui: String): Observable<HashMap<String, String>> {
        return yandexApiService.getLanguages(apiKey, ui)
                .subscribeOn(Schedulers.io())
                .flatMap {laguages->

                    val map = laguages.langs
                    val map1 = hashMapOf<String, String>()
                    val dirs = laguages.dirs

                    for (i in 0 until dirs.size){
                        val dir = dirs[i].split("-")
                        map1[dir[0]] = map.getValue(dir[0])
                    }

                    Observable.just(map1)
                }

    }

    @SuppressLint("CheckResult")
    override fun getTranslationLanguages(apiKey: String, ui: String, currentLanguage: String): Observable<List<String>>  {
         return yandexApiService.getLanguages(apiKey, ui)
                .subscribeOn(Schedulers.io())
                 .flatMap {laguages->

                     val map = laguages.langs
                     val list = linkedSetOf<String>()
                     val dirs = laguages.dirs

                     for (i in 0 until dirs.size){
                         val dir = dirs[i].split("-")
                         if (map.getValue(dir[0]) == currentLanguage) {
                             list.add(map.getValue(dir[1]))
                         }
                     }
                     Observable.just(list.toList())
                 }
    }

}