package com.surelabsid.lauwba.retribusipasar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.util.CrashUtils
import com.google.firebase.crashlytics.CrashlyticsRegistrar
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer("", true).schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
//                throw RuntimeException("Test Crash")
            }
        }, 3000)
    }
}