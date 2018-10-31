package com.example.yandextranslator.di

import com.example.yandextranslator.ui.AddTranslationFragment
import com.example.yandextranslator.ui.ListTranslationFragment
import com.example.yandextranslator.ui.settings.NotificationService
import com.example.yandextranslator.ui.settings.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBuilder {

   @ContributesAndroidInjector
   abstract fun bindAddTranlsationFragment(): AddTranslationFragment

   @ContributesAndroidInjector
   abstract fun bindListTranslationFragment(): ListTranslationFragment

   @ContributesAndroidInjector
   abstract fun bindSettingsActivity(): SettingsActivity

   @ContributesAndroidInjector
   abstract fun bindNotificationService(): NotificationService
}