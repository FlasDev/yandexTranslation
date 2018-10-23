package com.example.yandextranslator.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.yandextranslator.ui.AddTranslationFragment
import com.example.yandextranslator.ui.AddTranslationViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class YandexTranslationModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application
}