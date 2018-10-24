package com.example.yandextranslator.di

import android.app.Activity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandextranslator.ui.AddTranslationViewModel
import com.example.yandextranslator.ui.ListTranslationViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.AndroidViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(AddTranslationViewModel::class)
    abstract fun bindAddTranslationViewModel(viewModel: AddTranslationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListTranslationViewModel::class)
    abstract fun bindListTranslationViewModel(viewModel: ListTranslationViewModel): ViewModel
}