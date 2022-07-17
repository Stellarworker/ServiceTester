package com.stellarworker.servicetester.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stellarworker.servicetester.R
import com.stellarworker.servicetester.test.IntentData
import com.stellarworker.servicetester.test.MyIntentServiceTest
import com.stellarworker.servicetester.test.SystemIntentServiceTest

private const val TIME_TAG = "TIME"
private const val LABEL_TAG = "LABEL"
private val intentData = arrayOf(
    IntentData(TIME_TAG, 3L, LABEL_TAG, "Call 1"),
    IntentData(TIME_TAG, 1L, LABEL_TAG, "Call 2"),
    IntentData(TIME_TAG, 4L, LABEL_TAG, "Call 3"),
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startSystemIntentServiceTest(this)
        startMyIntentServiceTest(this)
    }

    private fun startSystemIntentServiceTest(context: Context) {
        testService(Intent(context, SystemIntentServiceTest()::class.java), intentData)
    }

    private fun startMyIntentServiceTest(context: Context) {
        testService(Intent(context, MyIntentServiceTest()::class.java), intentData)
    }

    private fun testService(intent: Intent, intentData: Array<IntentData>) {
        intentData.forEach { data ->
            startService(intent.apply {
                with(data) {
                    putExtra(timeTag, time)
                    putExtra(labelTag, label)
                }
            })
        }
    }
}
