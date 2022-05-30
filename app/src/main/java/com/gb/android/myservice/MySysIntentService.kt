package com.gb.android.myservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast

private const val TAG = "MySysIntentService"

class MySysIntentService : Service() {

    private val workerThread: HandlerThread = HandlerThread("MySysIntentService_Thread")

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        workerThread.start()
        Log.d(TAG, "onCreate() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Handler(workerThread.looper).postAtFrontOfQueue {
            onHandleIntent(intent)
        }
        Handler(workerThread.looper).post {
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun onHandleIntent(intent: Intent?) {
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

    companion object {
        private const val SYS_KEY = "MY_SYS_KEY"

        fun startMyService(context: Context, counter: Int) {
            val serviceIntent = Intent(context, MySysIntentService::class.java)
            serviceIntent.putExtra(SYS_KEY, counter)
            context.startService(serviceIntent)
            Log.d(TAG, "startMyService() called with: context = $context, counter = $counter")
        }
    }

}