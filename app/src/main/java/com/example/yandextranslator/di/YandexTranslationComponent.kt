package com.example.yandextranslator.di

import android.app.Application
import com.example.yandextranslator.YandexTranslationApplication
import com.example.yandextranslator.net.TranslationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AndroidBuilder::class,
    YandexTranslationModule::class,
    TranslationModule::class,
    ViewModelModule::class
])
interface YandexTranslationComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): YandexTranslationComponent
    }

    fun inject(application: YandexTranslationApplication)
}