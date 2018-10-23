package com.example.yandextranslator

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.example.yandextranslator.di.DaggerYandexTranslationComponent
import com.example.yandextranslator.di.YandexTranslationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class YandexTranslationApplication: Application(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val yandexTranslationComponent: YandexTranslationComponent by lazy {
        DaggerYandexTranslationComponent.builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        yandexTranslationComponent.inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}