package com.gb.android.myservice

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

private const val TAG = "SysIntentService"

class SysIntentService : IntentService("SysIntentService") {

    companion object {
        private const val SYS_KEY = "SYS_KEY"

        fun startService(context: Context, counter: Int) {
            val serviceIntent = Intent(context, SysIntentService::class.java)
            serviceIntent.putExtra(SYS_KEY, counter)
            context.startService(serviceIntent)
            Log.d(TAG, "startService() called with: context = $context, counter = $counter")
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent() called with: intent = $intent")
        val counter = intent?.getIntExtra(SYS_KEY, 0)
        Thread.sleep(5_000)
        when (counter) {
            0 -> {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "Counter is empty", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                for (i in 1..counter!!) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(this, "$i -- $TAG", Toast.LENGTH_SHORT).show()
                    }
                    Thread.sleep(1_000)
                }
            }
        }
    }

}