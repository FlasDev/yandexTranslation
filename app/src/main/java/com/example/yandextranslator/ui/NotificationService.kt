package com.example.yandextranslator.ui

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.yandextranslator.R
import com.example.yandextranslator.model.RealmTranslationRepository
import com.example.yandextranslator.model.TranslationRealmModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import java.util.*
import javax.inject.Inject

class NotificationService: Service() {

    private lateinit var nm: NotificationManager
    private lateinit var realm: Realm
    override fun onCreate() {
        super.onCreate()
        nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        realm = Realm.getDefaultInstance()
        Log.d("myLogs", "$realm")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Log.d("myLogs", "$realmTranslationRepository")
        createNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("CheckResult")
    private fun createNotification(){
        realm.where(TranslationRealmModel::class.java)
                .findAllAsync()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe {
                    val notificationBuilder =
                            Notification.Builder(baseContext)
                                    .setSmallIcon(R.drawable.ic_g_translate)
                                    .setContentTitle(it.inputText)
                                    .setContentText(it.translationText)
                                    .setAutoCancel(false)

                    val notification = notificationBuilder.build()

                    nm.notify(1, notification)
                }
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        fun newIntent(packageContext: Context): Intent{
            val intent = Intent(packageContext, NotificationService::class.java)
            return intent
        }
    }
}