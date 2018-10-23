package com.example.yandextranslator.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
class ViewModelFactory @Inject constructor(
        private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
        application: Application
        ) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = viewModelProviders[modelClass]
                ?: viewModelProviders.entries.first {
                    modelClass.isAssignableFrom(it.key)
                }.value ?: throw IllegalArgumentException("unknown model class $modelClass")

        return try {
            provider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}