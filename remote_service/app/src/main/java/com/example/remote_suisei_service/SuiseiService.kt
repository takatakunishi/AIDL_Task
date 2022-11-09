package com.example.remote_suisei_service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class SuiseiService : Service() {

    private var registeredSuiseiText: Array<String> = arrayOf(
        "しょたまちさん？\n「だから！\nショタは好きじゃ、\nないんだって！」",
        "彗星の如く現れたスターの原石！\nアイドルVTuberの星街すいせいです！"
    )

    private val binder = object : SuiseiInterface.Stub() {
        override fun getSuiseiWords(): String {
            Log.d("suisei service in service", "all registered text is...")
            for (text in registeredSuiseiText) {
                Log.d("suisei service in service", text)
            }
            return registeredSuiseiText.getOrElse(getRand()){"すいちゃんは〜〜？"}
        }

        override fun registerSuiseiWords(registerWord: String?) {
            if (registerWord == null) return
            registeredSuiseiText+=registerWord
            Log.d("suisei service in service", registerWord + "is registered")
            Log.d("suisei service in service", "registered text is...")
            for (text in registeredSuiseiText) {
                Log.d("suisei service in service", text)
            }
        }
    }

    override fun onCreate() {
        Log.d("suisei service in service", "create suisei service")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("suisei service in service", "start suisei service")
        Toast.makeText(this, "suisei service starting", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("suisei service in service","on unbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("suisei service in service", "stop suisei service")
        Toast.makeText(this, "suisei service stopped", Toast.LENGTH_SHORT).show()
    }

    private fun getRand() : Int {
        return (0 until registeredSuiseiText.size).random()
    }

}