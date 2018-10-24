package com.example.yandextranslator.ui


import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.yandextranslator.BR
import com.example.yandextranslator.R
import com.example.yandextranslator.databinding.FragmentListTranslationBinding
import com.example.yandextranslator.di.ViewModelFactory
import com.example.yandextranslator.ui.base.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject


class ListTranslationFragment : BaseFragment<FragmentListTranslationBinding, ListTranslationViewModel>() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun getLayout(): Int = R.layout.fragment_list_translation

    override fun getViewModel(): ListTranslationViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ListTranslationViewModel::class.java]

    override fun getVariable(): Int = BR.viewmodel

    override fun initUI() {
        val fab = viewDataBinding.addTranslationFab
        fab.setOnClickListener {
            navController.navigate(R.id.action_ListTranslationFragment_to_addTranslationFragment)
        }
    }
}
