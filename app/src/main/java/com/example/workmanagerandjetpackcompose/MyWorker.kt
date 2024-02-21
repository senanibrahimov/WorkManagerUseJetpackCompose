package com.example.workmanagerandjetpackcompose

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context:Context,workerparams:WorkerParameters):Worker(context,workerparams) {
    override fun doWork(): Result {
       val top=10+20
        Log.e("Arxa plan islemi",top.toString())

        return Result.success()
    }


}