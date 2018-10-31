package com.example.yandextranslator.ui.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.yandextranslator.R
import com.example.yandextranslator.ui.AppCompatPreferenceActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity: AppCompatPreferenceActivity(){

    private lateinit var am: AlarmManager
    private lateinit var pIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        addPreferencesFromResource(R.xml.settings)
        initUI()
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pIntent = PendingIntent.getService(this, 0, NotificationService.newIntent(this), PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun initUI() {
        setAppbar(setting_toolbar)
        setPreferencesTools()
    }

    private fun setPreferencesTools() {
        findPreference("cb_notification").setOnPreferenceChangeListener { preference, newValue ->
            val value = newValue as Boolean
            if (value) {
                am.setRepeating(AlarmManager.RTC_WAKEUP,
                        SystemClock.currentThreadTimeMillis(),
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES / 15,
                        pIntent)
            }else{
                cancelAm()
            }
            true
        }
    }

    private fun setAppbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun cancelAm(){
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