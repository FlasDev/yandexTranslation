package com.example.yandextranslator.net

import com.example.yandextranslator.util.KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface YandexApiService {
    @GET("api/v1.5/tr.json/getLangs")
    fun getLanguages(
            @Query("key")key: String = KEY,
            @Query("ui")ui: String
    ): Observable<Languages>

    @POST("https://translate.yandex.net/api/v1.5/tr.json/translate")
    fun getTranslate(
            @Query("key")key: String = KEY,
            @Query("text")text: String,
            @Query("lang")lang: String
    ): Observable<Translate>
}