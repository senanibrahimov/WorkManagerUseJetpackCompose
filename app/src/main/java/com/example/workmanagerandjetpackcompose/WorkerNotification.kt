package com.example.workmanagerandjetpackcompose

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerNotification(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {
    override fun doWork(): Result {

         createNotifications(applicationContext)
        return Result.success()
    }



    fun createNotifications(context: Context){
        val builder: NotificationCompat.Builder
        val notManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            val kanal_id="kanalid"
            val kanal_name="mychannel"
            val kanaltanit="kanaltanitim"
            val prio= NotificationManager.IMPORTANCE_HIGH

            var chanel: NotificationChannel?=notManager.getNotificationChannel(kanal_id)
            if (chanel==null){
                chanel= NotificationChannel(kanal_id,kanal_name,prio)
                chanel.description=kanaltanit
                notManager.createNotificationChannel(chanel)
            }

            builder= NotificationCompat.Builder(context,kanal_id)

            builder.setSmallIcon(R.drawable.baseline_add_a_photo_24)
                .setContentTitle("Camera")
                .setContentText("Elnardan mesaj")
                .setAutoCancel(true)

        }else{
            builder= NotificationCompat.Builder(context)

            builder.setSmallIcon(R.drawable.baseline_add_a_photo_24)
                .setContentTitle("Camera")
                .setContentText("Elnardan mesaj")
                .setAutoCancel(true)
                .priority= Notification.PRIORITY_HIGH
        }

        notManager.notify(1,builder.build())
    }
}