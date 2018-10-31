package com.example.yandextranslator.model

import com.example.yandextranslator.net.Languages
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TranslationRealmModel(@PrimaryKey var id: String="",
                                 var inputText: String="",
                                 var translationText: String="",
                                 var languages: String=""
): RealmObject()
