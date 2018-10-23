package com.example.yandextranslator.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import javax.inject.Inject

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    @Inject lateinit var realm: Realm

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        realm.close()
        super.onCleared()
    }
}