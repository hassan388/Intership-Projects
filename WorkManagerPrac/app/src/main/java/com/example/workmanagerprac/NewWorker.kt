package com.example.workmanagerprac

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class NewWorker( cont: Context,param : WorkerParameters ): Worker(cont,param) {


    override fun doWork(): Result {

        makeStatusNotification("APP IS RUNNING",applicationContext)
        val text = inputData.getString(Constants.KEY_USER_COMMENT_TEXT)
       return try{
            val outputData = Data.Builder()
                .putString(Constants.KEY_USER_SKILL_LEVEL,getSkill(text))
                .build()

             Result.success(outputData)

        }catch (e: Exception){
            Result.failure()
        }



        return Result.success()
    }


    fun getSkill(userText: String?) : String{

        val emotionList = listOf("Great","Fantastic","Average","Useless","Poor","Genius","Dum")
        return emotionList.random()

    }


    }

//Make Notification
fun makeStatusNotification(message: String, context: Context) {
    //make a Notification Channel if Necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val channelName = Constants.NOTIFICATION_CHANNEL_NAME
        val channelDescription = Constants.NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(Constants.CHANNEL_ID,channelName,importance)
        notificationChannel.description = channelDescription

        //Adding the Channel
        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
    val notificationPriorityHigh = NotificationCompat.PRIORITY_HIGH

    //Now create the Notification
    val notificationBuilder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(Constants.NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(notificationPriorityHigh)
        .setVibrate(LongArray(0))

    //show notification
    NotificationManagerCompat.from(
        context
    ).notify(
        Constants.NOTIFICATION_ID,notificationBuilder.build()
    )
}
