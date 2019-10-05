package com.edge.inappupdate

import com.google.android.play.core.appupdate.AppUpdateInfo

public interface UpdateListener {
    fun onUpdateChecked(appUpdateInfo: AppUpdateInfo,updateAvailable:Boolean)
    fun onUpdateCheckFailure(exception: Exception?)
}