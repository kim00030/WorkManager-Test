package com.dan.workmanagertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.dan.workmanagertest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    companion object {
        const val DEBUG_TAG = "myDebug"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            //setOneTimeRequest()
            //setOneTimeRequestWithConstraint()
            setOneTimeRequestRunsWhenInternetIsAcitive()
        }
    }

    private fun setOneTimeRequest() {

        val workManger = WorkManager.getInstance(applicationContext)

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .build()
        workManger.enqueue(uploadRequest)

        workManger.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this) {
                binding.txtDisplay.text = it.state.name //return "Success" or "Failure"
            }

    }

    /**
     * WorkManager runs with constraint which means, it runs only in some specific condition
     * for example, we push it to be run when a device is on charged
     *
     * If a emulator's battery charge is not connected, It would not run
     */
    private fun setOneTimeRequestWithConstraint() {

        val workManger = WorkManager.getInstance(applicationContext)

        //set constraints
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                //set constraints
            .setConstraints(constraints)
            .build()
        workManger.enqueue(uploadRequest)

        workManger.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this) {
                binding.txtDisplay.text = it.state.name //return "Success" or "Failure"
            }

    }

    /**
     * It will be run when internet is active
     */
    private fun setOneTimeRequestRunsWhenInternetIsAcitive() {

        val workManger = WorkManager.getInstance(applicationContext)

        //set to run it only when internet is on active
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .build()
        workManger.enqueue(uploadRequest)

        workManger.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this) {
                binding.txtDisplay.text = it.state.name //return "Success" or "Failure"
            }

    }
}


/**
 * WorkManager has 2 types
 * -Periodic Work Request
 * -One Time work Request
 *
 * steps:
 * 1. Create a worker class
 * 2. Create a work request
 * 3. Enqueue the request
 * 4. Get the status updates
 * Tutorial URL: https://www.youtube.com/watch?v=HNYr1ay3yjo
 */