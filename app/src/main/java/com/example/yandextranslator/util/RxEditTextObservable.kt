package com.example.yandextranslator.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxEditTextObservable{

    fun fromView(editText: EditText): Observable<String> {
        val subject: PublishSubject<String> = PublishSubject.create()
        editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                subject.onNext(s.toString())
            }
        })

        return subject
    }
}