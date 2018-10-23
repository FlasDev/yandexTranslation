package com.example.yandextranslator.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.yandextranslator.ui.AddTranslationFragment
import com.example.yandextranslator.ui.AddTranslationViewModel
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class YandexTranslationModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application


    @Provides
    @Singleton
    fun provideRealmDatabase(context: Application): Realm{
        Realm.init(context)
        val configuration = RealmConfiguration
                .Builder()
                .name("translation.realm")
                .build()
        Realm.deleteRealm(configuration)
        Realm.setDefaultConfiguration(configuration)

        return Realm.getDefaultInstance()
    }
}