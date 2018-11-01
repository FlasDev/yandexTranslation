package com.example.yandextranslator.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.AndroidException
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.example.yandextranslator.YandexTranslationApplication
import com.example.yandextranslator.model.RealmTranslationRepository
import com.example.yandextranslator.model.TranslationRealmModel
import com.example.yandextranslator.model.YandexTranslationRepository
import com.example.yandextranslator.net.Languages
import com.example.yandextranslator.net.YandexApiService
import com.example.yandextranslator.ui.base.BaseViewModel
import com.example.yandextranslator.util.KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class AddTranslationViewModel @Inject constructor(private val yandexTranslationRepository: YandexTranslationRepository,
                                                  private val realmTranslationRepository: RealmTranslationRepository,
                                                  application: Application) : BaseViewModel(application) {

    private var mapOfLanguages: HashMap<String, String>? = null
    val currentLanguages: ObservableField<List<String>> = ObservableField()
    val translateLanguages: ObservableField<List<String>> = ObservableField()
    val translationField: ObservableField<String> = ObservableField()

    @SuppressLint("CheckResult")
    fun getLanguages(){
        val disposable = yandexTranslationRepository.getLanguages(apiKey = KEY, ui = "ru")
                .doOnNext{if (mapOfLanguages == null) mapOfLanguages = it}
                .map {hashmap->
                    val list = hashmap.values.toList().sorted().toMutableList()
                   // list.add(0, "Английский")
                    list
                }
                .subscribe(currentLanguages::set)

        compositeDisposable.add(disposable)
    }

    @SuppressLint("CheckResult")
    fun getTranslateLanguages(currentLanguagesId: Int){
        val disposable = yandexTranslationRepository.getTranslationLanguages(apiKey = KEY, ui = "ru", currentLanguage = currentLanguages.get()!![currentLanguagesId])
                .subscribe(translateLanguages::set)

        compositeDisposable.add(disposable)
    }

    @SuppressLint("CheckResult")
    fun getTranslate(text: String, currentLanguageId: Int, translationLanguage: Int){
        val language = getLanguageToTransalteById(currentLanguageId, translationLanguage)
        val disposable = yandexTranslationRepository.getTranslate(apiKey = KEY, text = text, language = language)
                .doOnNext(translationField::set)
                .map {TranslationRealmModel(
                        id = UUID.randomUUID().toString(),
                        inputText = text,
                        translationText = it,
                        languages = language)}
                .subscribe(realmTranslationRepository::saveTranslation)

        compositeDisposable.add(disposable)
    }

    fun saveToDatabase(inputText: String, translationText: String, currentLanguageId: Int, translationLanguage: Int){
        realmTranslationRepository.saveTranslation(TranslationRealmModel(
                id = UUID.randomUUID().toString(),
                inputText = inputText,
                translationText = translationText,
                languages = getLanguageToTransalteById(currentLanguageId, translationLanguage)
        ))
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

    override fun onCleared() {
        realmTranslationRepository.closeRealm()
        super.onCleared()
    }
}