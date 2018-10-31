package com.example.yandextranslator.ui.settings

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.yandextranslator.R
import com.example.yandextranslator.model.RealmTranslationRepository
import dagger.android.AndroidInjection
import io.realm.Realm
import javax.inject.Inject

class NotificationService: Service() {

    private lateinit var nm: NotificationManager
    private lateinit var realm: Realm

    @Inject
    lateinit var realmTranslationRepository: RealmTranslationRepository
    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        realm = Realm.getDefaultInstance()
        Log.d("myLogs", "$realmTranslationRepository")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Log.d("myLogs", "$realmTranslationRepository")
        createNotification(intent!!)
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("CheckResult")
    private fun createNotification(intent: Intent){
        val pI = PendingIntent.getActivity(baseContext, 0, intent, 0)
        val randomWord = realmTranslationRepository.loadRandomWord()

        val notificationBuilder =
                Notification.Builder(baseContext)
                        .setSmallIcon(R.drawable.ic_g_translate)
                        .setContentTitle(randomWord!!.inputText)
                        .setContentText(randomWord.translationText)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_navigate_next, "Next word", pI)

        val notification = notificationBuilder.build()

        nm.notify(1, notification)

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