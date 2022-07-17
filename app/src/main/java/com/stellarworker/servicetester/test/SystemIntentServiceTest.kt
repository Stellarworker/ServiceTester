package com.stellarworker.servicetester.test

import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.util.concurrent.TimeUnit

private const val DEFAULT_SERVICE_NAME = "SystemIntentService"
private const val LOG_TAG = DEFAULT_SERVICE_NAME
private const val TIME_TAG = "TIME"
private const val LABEL_TAG = "LABEL"
private const val DEFAULT_TIME_VALUE = 0L
private const val ON_CREATE_MESSAGE = "onCreate()"
private const val ON_HANDLE_INTENT_START_MESSAGE = "onHandleIntent() start "
private const val ON_HANDLE_INTENT_END_MESSAGE = "onHandleIntent() end "
private const val ON_DESTROY_MESSAGE = "onDestroy()"

class SystemIntentServiceTest(private val serviceName: String) : IntentService(serviceName) {
    constructor() : this(DEFAULT_SERVICE_NAME)

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, ON_CREATE_MESSAGE)
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        intent?.let { notNullIntent ->
            val time = notNullIntent.getLongExtra(TIME_TAG, DEFAULT_TIME_VALUE)
            val label = notNullIntent.getStringExtra(LABEL_TAG)
            Log.d(LOG_TAG, ON_HANDLE_INTENT_START_MESSAGE + label)
            try {
                TimeUnit.SECONDS.sleep(time)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            Log.d(LOG_TAG, ON_HANDLE_INTENT_END_MESSAGE + label)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, ON_DESTROY_MESSAGE)
    }
}