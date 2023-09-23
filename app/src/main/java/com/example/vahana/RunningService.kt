package com.example.vahana
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.security.Provider.Service
import java.util.ArrayList

class RunningService:android.app.Service() {


    override fun onBind(p0: Intent?):IBinder?{
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString()->start()
            Actions.STOP.toString()->stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    private fun start(){
        val notification=NotificationCompat.Builder(this,"running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Data Updating Every 10 Seconds")
            .build()
        startForeground(1,notification)

    }
    enum class Actions{
        START,STOP
    }
}