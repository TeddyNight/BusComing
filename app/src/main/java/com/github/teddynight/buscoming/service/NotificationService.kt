package com.github.teddynight.buscoming.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.teddynight.buscoming.MainActivity
import com.github.teddynight.buscoming.R
import com.github.teddynight.buscoming.data.model.Line
import com.github.teddynight.buscoming.data.repository.StationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationService: Service() {
    private val handler = Handler()
    private val stnRepository = StationRepository

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("Notification", "BusComing通知服务",
                NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "Notification")
            .setContentTitle("BusComing")
            .setContentText("BusComing通知服务正在运行")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground))
            .setContentIntent(pi)
            .build()
        handler.postDelayed(Runnable { refresh() },30000)
        startForeground(1, notification)
    }

    fun refresh() {
        Log.d("NotificationService","Updating..")
        GlobalScope.launch(stnRepository.job) {
            try {
                stnRepository.refresh()
            } catch (e: Throwable) {
                Log.e("NotificationService",e.message!!)
            }
        }
        busNotify()
        handler.postDelayed(Runnable {
            refresh() },30000)
    }

    fun busNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        fun helper(line: Line) {
            val arrivals = line.arrivals
            var waitingTime: Long = -1
            if (!arrivals.isEmpty()) {
                val time = arrivals.sorted()[0]
                val curTime = System.currentTimeMillis()
                if (time > curTime) waitingTime  = ((time - curTime) / (60 * 1000))
                if (waitingTime in 0 .. 2) {
                    val notification = NotificationCompat.Builder(this, "Notification")
                        .setContentTitle(line.name)
                        .setContentText("终点站:${line.endSn}，还有${waitingTime}分钟到达")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground))
                        .build()
                    manager.notify(1, notification)
                }
            }
        }
        val lines = stnRepository.lines
        if (lines.value != null) {
            lines.value!!.forEach() {
                helper(it[0])
                helper(it[1])
            }
        }
    }
}