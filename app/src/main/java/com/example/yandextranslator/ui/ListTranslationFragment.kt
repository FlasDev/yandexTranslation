package com.example.yandextranslator.ui


import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yandextranslator.BR
import com.example.yandextranslator.R
import com.example.yandextranslator.databinding.FragmentListTranslationBinding
import com.example.yandextranslator.di.ViewModelFactory
import com.example.yandextranslator.model.TranslationRealmModel
import com.example.yandextranslator.ui.base.BaseFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class ListTranslationFragment : BaseFragment<FragmentListTranslationBinding, ListTranslationViewModel>() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun getLayout(): Int = R.layout.fragment_list_translation

    override fun getViewModel(): ListTranslationViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ListTranslationViewModel::class.java]

    override fun getVariable(): Int = BR.viewmodel

    override fun onResume() {
        super.onResume()
        val fab = activity?.findViewById<FloatingActionButton>(R.id.main_fab)
        val bottomAppBar = activity?.findViewById<BottomAppBar>(R.id.main_bottom_app_bar)
        fab?.hide(object: FloatingActionButton.OnVisibilityChangedListener(){
            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                bottomAppBar!!.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab?.setImageDrawable(resources.getDrawable(R.drawable.ic_g_translate, null))
                fab?.show()
            }
        })
    }

    @SuppressLint("CheckResult")
    override fun initUI() {

        val fab = activity?.findViewById<FloatingActionButton>(R.id.main_fab)
        val bottomAppBar = activity?.findViewById<BottomAppBar>(R.id.main_bottom_app_bar)
        fab?.setOnClickListener {
            navController.navigate(R.id.action_ListTranslationFragment_to_addTranslationFragment)
            fab.hide(object: FloatingActionButton.OnVisibilityChangedListener(){
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    bottomAppBar!!.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    bottomAppBar.showOverflowMenu()
                    fab?.setImageDrawable(resources.getDrawable(R.drawable.ic_check, null))
                    fab?.show()
                }
            })
        }
        //viewDataBinding.emptyTranslationListViewstub.viewStub?.inflate()

        val recyclerView = viewDataBinding.listTranslateRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = TranslationAdapter()
        getViewModel().getTranslationList()!!.subscribe {
            (recyclerView.adapter as TranslationAdapter).setData(it)
        }


    }
}
