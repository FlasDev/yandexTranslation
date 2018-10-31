package com.example.yandextranslator.util

import androidx.recyclerview.widget.DiffUtil
import com.example.yandextranslator.model.TranslationRealmModel

class TranslationListDiffCallback(private val oldList: List<TranslationRealmModel>,
                                  private val newList: List<TranslationRealmModel>
): DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size


}