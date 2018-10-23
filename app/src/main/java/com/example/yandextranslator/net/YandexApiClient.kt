package com.example.yandextranslator.net

import io.reactivex.Observable

interface YandexApiClient {

    fun getLanguages(apiKey: String, ui: String): Observable<HashMap<String, String>>

    fun getTranslationLanguages(apiKey: String, ui: String, currentLanguage: String): Observable<List<String>>

    fun getTranslate(apiKey: String, text: String, language: String): Observable<String>
}