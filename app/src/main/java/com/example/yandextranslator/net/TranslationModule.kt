package com.example.yandextranslator.net

import com.example.yandextranslator.util.YANDEX_TRANSLATE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class TranslationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(YANDEX_TRANSLATE_URL)
                    .build()

    @Provides
    @Singleton
    fun provideYandexApiService(retrofit: Retrofit): YandexApiService =
            retrofit.create(YandexApiService::class.java)

}