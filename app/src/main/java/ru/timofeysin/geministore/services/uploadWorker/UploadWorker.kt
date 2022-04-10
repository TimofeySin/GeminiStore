package ru.timofeysin.geministore.services.uploadWorker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.timofeysin.geministore.services.retrofit.TakeInternetData


class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    val context = appContext
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        uploadOrders()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }

    private fun uploadOrders() {
        CoroutineScope(Dispatchers.IO).launch {
            TakeInternetData().saveDataOrder(context)
        }



    }
}

