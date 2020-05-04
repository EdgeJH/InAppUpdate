package com.edge.inappupdate

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallState

public interface UpdateListener {
    fun onUpdateChecked(appUpdateInfo: AppUpdateInfo,updateAvailable:Boolean)
    fun onUpdateCheckFailure(exception: Exception?)
    fun onUpdateState(installState : InstallState, bytesDownLoaded : Long ,totalBytesToDownLoaded : Long)
}