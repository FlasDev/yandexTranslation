package com.example.yandextranslator.ui


import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProviders
import com.example.yandextranslator.BR
import com.example.yandextranslator.R
import com.example.yandextranslator.databinding.FragmentAddTranslationBinding
import com.example.yandextranslator.di.ViewModelFactory
import com.example.yandextranslator.ui.base.BaseFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import javax.inject.Inject


class AddTranslationFragment : BaseFragment<FragmentAddTranslationBinding, AddTranslationViewModel>(){

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun getVariable(): Int = BR.viewmodel

    override fun getLayout(): Int = R.layout.fragment_add_translation

    override fun getViewModel(): AddTranslationViewModel =
            ViewModelProviders.of(this, viewModelFactory)[AddTranslationViewModel::class.java]

    override fun onResume() {
        super.onResume()
        getViewModel().getLanguages()
    }

    @SuppressLint("CheckResult")
    override fun initUI() {
        RxAdapterView.itemSelections(viewDataBinding.spinnerCurrentLanguages)
                .skip(1)
                .subscribe(getViewModel()::getTranslateLanguages)

        RxView.clicks(viewDataBinding.btnTranslate)
                .map {viewDataBinding.textToTranslateField.text.toString()}
                .filter{it.isNotEmpty()}
                .subscribe {getViewModel().getTranslate(
                        it,
                        viewDataBinding.spinnerCurrentLanguages.selectedItemPosition,
                        viewDataBinding.spinnerTranslationLanguages.selectedItemPosition
                        )}
    }

}
