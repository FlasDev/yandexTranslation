package com.example.yandextranslator

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.example.yandextranslator.di.DaggerYandexTranslationComponent
import com.example.yandextranslator.di.YandexTranslationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class YandexTranslationApplication: Application(), HasSupportFragmentInjector, HasActivityInjector, HasServiceInjector {

    @Inject lateinit var dispatchingAndroidInjectorService: DispatchingAndroidInjector<Service>
    override fun serviceInjector(): AndroidInjector<Service> = dispatchingAndroidInjectorService

    @Inject
    lateinit var dispatchingAndroidInjectorFragment: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjectorFragment

    @Inject
    lateinit var dispatchingAndroidInjectorActivity: DispatchingAndroidInjector<Activity>
    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjectorActivity

    private val yandexTranslationComponent: YandexTranslationComponent by lazy {
        DaggerYandexTranslationComponent.builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        yandexTranslationComponent.inject(this)
    }





}