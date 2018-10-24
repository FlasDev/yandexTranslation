package com.example.yandextranslator.di

import com.example.yandextranslator.ui.AddTranslationFragment
import com.example.yandextranslator.ui.ListTranslationFragment
import com.example.yandextranslator.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBuilder {

   @ContributesAndroidInjector
   abstract fun bindAddTranlsationFragment(): AddTranslationFragment

   @ContributesAndroidInjector
   abstract fun bindListTranslationFragment(): ListTranslationFragment
}