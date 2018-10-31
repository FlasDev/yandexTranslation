package com.example.yandextranslator.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import javax.inject.Inject

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {


    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}