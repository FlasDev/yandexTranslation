package com.example.yandextranslator.ui.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.preference.Preference
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.preference.PreferenceFragment
import com.example.yandextranslator.R
import com.example.yandextranslator.ui.AppCompatPreferenceActivity
import com.example.yandextranslator.ui.NotificationService
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity: AppCompatPreferenceActivity(){

    private lateinit var am: AlarmManager
    private lateinit var pIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        addPreferencesFromResource(R.xml.settings)
        setSupportActionBar(setting_toolbar)
        supportActionBar?.apply {
            title = getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
        }
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pIntent = PendingIntent.getService(this, 0, NotificationService.newIntent(this), PendingIntent.FLAG_CANCEL_CURRENT)
        findPreference("cb_notification").setOnPreferenceChangeListener { preference, newValue ->
            val value = newValue as Boolean
            if (value) {
                am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.currentThreadTimeMillis(), 6000, pIntent)
            }else{
                cancleAm()
            }
            true
        }
    }

    private fun cancleAm(){
        am.cancel(pIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}