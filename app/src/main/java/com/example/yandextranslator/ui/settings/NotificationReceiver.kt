package com.example.yandextranslator.ui.settings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.startActivity(Intent(context, NotificationService::class.java))
    }
}