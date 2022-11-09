package com.example.itarchitecture1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.remote_suisei_service.SuiseiInterface

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val startServiceBtn = findViewById<Button>(R.id.start_suisei_service)
        val stopServiceBtn = findViewById<Button>(R.id.stop_suisei_service)

        startServiceBtn.setOnClickListener{
            val intent = Intent().also {
                it.action = "com.example.remote_suisei_service.SUISEI_SERVER"
                it.`package` = "com.example.remote_suisei_service"
            }
            startService(intent)
        }
        stopServiceBtn.setOnClickListener{
            Log.d("suisei second activity", "stop btn pushed")
            val intent = Intent().also {
                it.action = "com.example.remote_suisei_service.SUISEI_SERVER"
                it.`package` = "com.example.remote_suisei_service"
            }
            // 他のフラグメントでbindした場合、
            // unbindしていないとコネクションがきれないため、
            // ここのstopServiceは役割を果たしません。
            stopService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("second activity", "activity destroyed")
    }
}