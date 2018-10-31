package com.example.yandextranslator.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.yandextranslator.databinding.TranslationListItemBinding
import com.example.yandextranslator.model.TranslationRealmModel
import com.example.yandextranslator.R
import com.example.yandextranslator.util.TranslationListDiffCallback
import com.google.android.material.behavior.SwipeDismissBehavior

class TranslationAdapter: RecyclerView.Adapter<TranslationAdapter.ViewHolder>(){

    private var list: ArrayList<TranslationRealmModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<TranslationListItemBinding>(inflater, R.layout.translation_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(realmModel: ArrayList<TranslationRealmModel>){
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(TranslationListDiffCallback(this.list, realmModel))
        this.list = realmModel
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(var binding: TranslationListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(translation: TranslationRealmModel) {
            binding.model = translation
            binding.executePendingBindings()
        }

    }
}