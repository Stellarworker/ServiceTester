package com.stellarworker.servicetester.service

import android.app.Service
import android.content.Intent
import android.os.*
import androidx.annotation.WorkerThread

private const val INTENT_SERVICE_TAG = "IntentService[%1s]"

abstract class MyIntentService(private val serviceName: String) : Service() {

    @Volatile
    private lateinit var serviceLooper: Looper

    @Volatile
    private lateinit var serviceHandler: ServiceHandler
    var intentRedelivery = false

    inner class ServiceHandler(looper: Looper) : Handler(looper) {

        @Override
        override fun handleMessage(message: Message) {
            onHandleIntent(message.obj as Intent)
            stopSelf(message.arg1)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val thread = HandlerThread(String.format(INTENT_SERVICE_TAG, serviceName))
        thread.start()
        serviceLooper = thread.looper
        serviceHandler = ServiceHandler(serviceLooper)
    }

    @Deprecated("Deprecated in Java")
    override fun onStart(intent: Intent?, startId: Int) {
        serviceHandler.sendMessage(serviceHandler.obtainMessage().apply {
            arg1 = startId
            obj = intent
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onStart(intent, startId)
        return if (intentRedelivery) START_REDELIVER_INTENT else START_NOT_STICKY
    }

    override fun onDestroy() {
        serviceLooper.quit()
    }

    override fun onBind(intent: Intent): IBinder? = null

    @WorkerThread
    protected abstract fun onHandleIntent(intent: Intent?)
}
