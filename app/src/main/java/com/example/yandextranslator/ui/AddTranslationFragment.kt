package com.example.yandextranslator.ui


import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.ViewModelProviders
import com.example.yandextranslator.BR
import com.example.yandextranslator.R
import com.example.yandextranslator.databinding.FragmentAddTranslationBinding
import com.example.yandextranslator.di.ViewModelFactory
import com.example.yandextranslator.ui.base.BaseFragment
import com.example.yandextranslator.util.RxEditTextObservable
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.support.design.widget.RxFloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.text
import kotlinx.android.synthetic.main.fragment_add_translation.*
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class AddTranslationFragment : BaseFragment<FragmentAddTranslationBinding, AddTranslationViewModel>(){

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun getVariable(): Int = BR.viewmodel

    override fun getLayout(): Int = R.layout.fragment_add_translation

    override fun getViewModel(): AddTranslationViewModel =
            ViewModelProviders.of(this, viewModelFactory)[AddTranslationViewModel::class.java]

    override fun onResume() {
        super.onResume()
        //val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        //viewDataBinding.viewmodel?.currentLanguages?.set(listOf("Английский"))
        getViewModel().getLanguages()
    }

    @SuppressLint("CheckResult")
    override fun initUI() {
        RxView.clicks(activity?.findViewById(R.id.main_fab)!!)
                .filter{!TextUtils.isEmpty(viewDataBinding.textToTranslateField.text.toString()) &&
                        !TextUtils.isEmpty(viewDataBinding.translateTextField.text.toString())
                }
                .subscribe {
                    getViewModel().saveToDatabase(
                            viewDataBinding.textToTranslateField.text.toString(),
                            viewDataBinding.translateTextField.text.toString(),
                            viewDataBinding.spinnerCurrentLanguages.selectedItemPosition,
                            viewDataBinding.spinnerTranslationLanguages.selectedItemPosition
                    )
                    navController.navigate(R.id.ListTranslation)
                }
        RxAdapterView.itemSelections(viewDataBinding.spinnerCurrentLanguages)
                .skip(1)
                .subscribe(getViewModel()::getTranslateLanguages)

        RxView.clicks(viewDataBinding.clearTextField)
                .subscribe {
                    viewDataBinding.translateTextField.text = ""
                    viewDataBinding.textToTranslateField.setText("")
                }

        RxEditTextObservable.fromView(viewDataBinding.textToTranslateField)
                .skip(1)
                .debounce(800, TimeUnit.MILLISECONDS)
                .filter {it.isNotEmpty()}
                .subscribe {getViewModel().getTranslate(
                        it,
                        viewDataBinding.spinnerCurrentLanguages.selectedItemPosition,
                        viewDataBinding.spinnerTranslationLanguages.selectedItemPosition
                )}

    }

    @SuppressLint("CommitPrefEdits")
    override fun onPause() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putStringSet(getString(R.string.save_languages), setOf(
                    viewDataBinding.spinnerCurrentLanguages.selectedItem.toString(),
                    viewDataBinding.spinnerTranslationLanguages.selectedItem.toString()
            ))
            apply()
        }
        super.onPause()
    }
}
