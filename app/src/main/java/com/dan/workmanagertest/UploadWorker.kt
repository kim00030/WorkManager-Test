package com.dan.workmanagertest

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by Dan Kim on 2022-03-16
 */
class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

        return try {
            //Simulating uploading something
            for (i in 0..60000) {
                Log.d(MainActivity.DEBUG_TAG, "Uploading $i")
            }
            Result.success()

        } catch (e: Exception) {
            Result.failure()
        }
    }
}