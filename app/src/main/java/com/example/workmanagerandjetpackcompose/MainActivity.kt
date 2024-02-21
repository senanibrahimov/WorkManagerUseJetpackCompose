package com.example.workmanagerandjetpackcompose

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanagerandjetpackcompose.ui.theme.WorkManagerandJetpackComposeTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkManagerandJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Page()
                }
            }
        }
    }
}

@Composable
fun Page() {
    val con=LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = { createNotifications(con) }) {
            Text(text = "Notifications button")
        }
        Button(onClick = {

          //  onetimerequest(con)

            val peridicR= PeriodicWorkRequestBuilder<WorkerNotification>(15,TimeUnit.MINUTES).build()
            WorkManager.getInstance(con).enqueue(peridicR)
        }) {
            Text(text = "first button")
        }
    }
}

fun createNotifications(context: Context){
    val builder:NotificationCompat.Builder
    val notManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

        val kanal_id="kanalid"
        val kanal_name="mychannel"
        val kanaltanit="kanaltanitim"
        val prio=NotificationManager.IMPORTANCE_HIGH

        var chanel:NotificationChannel?=notManager.getNotificationChannel(kanal_id)
        if (chanel==null){
            chanel=NotificationChannel(kanal_id,kanal_name,prio)
            chanel.description=kanaltanit
            notManager.createNotificationChannel(chanel)
        }

        builder=NotificationCompat.Builder(context,kanal_id)

        builder.setSmallIcon(R.drawable.baseline_add_a_photo_24)
            .setContentTitle("Camera")
            .setContentText("Elnardan mesaj")
            .setAutoCancel(true)

    }else{
        builder=NotificationCompat.Builder(context)

        builder.setSmallIcon(R.drawable.baseline_add_a_photo_24)
            .setContentTitle("Camera")
            .setContentText("Elnardan mesaj")
            .setAutoCancel(true)
            .priority=Notification.PRIORITY_HIGH
    }

    notManager.notify(1,builder.build())
}
fun onetimerequest(context: Context){
     val constrint=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    val request= OneTimeWorkRequestBuilder<MyWorker>().setConstraints(constrint).setInitialDelay(10,TimeUnit.SECONDS).build()
      WorkManager.getInstance(context).enqueue(request)
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkManagerandJetpackComposeTheme {
        Page()
    }
}