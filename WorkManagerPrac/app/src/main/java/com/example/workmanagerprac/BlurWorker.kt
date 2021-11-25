package com.example.workmanagerprac

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters



private const val  TAG = "BlurWorker"
class BlurWorker(context: Context,parameters: WorkerParameters): Worker(context,parameters) {


    override fun doWork(): Result {
        val appContex = applicationContext

        //makeStatusNotification("Blurring Image...", appContex)

        return try {
                val picture = BitmapFactory.decodeResource(
                    appContex.resources, R.drawable.raya)
          /*  val output = blurBitmap(picture,appContex)
            val outputUri = writeBitmapToFile(appContex,output)
            makeStatusNotification("output is $outputUri",appContex)*/

            Result.success()
        }catch (throwable : Throwable){
            Log.e(TAG,"Error applying blur")
            Result.failure()
        }
    }
}