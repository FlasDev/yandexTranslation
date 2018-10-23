package com.example.yandextranslator.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.example.yandextranslator.model.YandexTranslationRepository
import com.example.yandextranslator.net.Languages
import com.example.yandextranslator.net.YandexApiService
import com.example.yandextranslator.ui.base.BaseViewModel
import com.example.yandextranslator.util.KEY
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class AddTranslationViewModel @Inject constructor(private val yandexTranslationRepository: YandexTranslationRepository,
                                                  application: Application) : BaseViewModel(application) {

    private var mapOfLanguages: HashMap<String, String>? = null
    val currentLanguages: ObservableField<List<String>> = ObservableField()
    val translateLanguages: ObservableField<List<String>> = ObservableField()
    val translationField: ObservableField<String> = ObservableField()

    @SuppressLint("CheckResult")
    fun getLanguages(){
        yandexTranslationRepository.getLanguages(apiKey = KEY, ui = "ru")
                .doOnNext{if (mapOfLanguages == null) mapOfLanguages = it}
                .map {it.values.toList().sorted()}
                .subscribe(currentLanguages::set)
    }

    @SuppressLint("CheckResult")
    fun getTranslateLanguages(currentLanguagesId: Int){
        yandexTranslationRepository.getTranslationLanguages(apiKey = KEY, ui = "ru", currentLanguage = currentLanguages.get()!![currentLanguagesId])
                .subscribe(translateLanguages::set)

    }

    @SuppressLint("CheckResult")
    fun getTranslate(text: String, currentLanguageId: Int, translationLanguage: Int){
        val language = getLanguageToTransalteById(currentLanguageId, translationLanguage)
        yandexTranslationRepository.getTranslate(apiKey = KEY, text = text, language = language)
                .subscribe(translationField::set)
    }

    private fun getLanguageToTransalteById(currentLanguageId: Int, translationLanguage: Int): String{
        val current = currentLanguages.get()!![currentLanguageId]
        val translation = translateLanguages.get()!![translationLanguage]
        var lang="$current-$translation"
        mapOfLanguages?.filterValues {
            it.contains(current) || it.contains(translation)
        }.apply {
            for (hash in this!!){
                if (current == hash.value){
                    lang = lang.replace(current, hash.key)
                }else if (translation == hash.value){
                    lang = lang.replace(translation, hash.key)
                }
            }
        }

        return lang
    }

}