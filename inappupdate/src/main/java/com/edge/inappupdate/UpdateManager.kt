package com.edge.inappupdate

import android.app.Activity
import android.graphics.Color
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task


class UpdateManager private constructor(private val activity: Activity,
                                        updateType : UpdateType,
                                        private val snackBarMessage : String?,
                                        private val snackbarBtnColor : Int = Color.YELLOW) {

    constructor(builder : Builder) : this(builder.activity!!,builder.updateType!!,builder.snackBarMessage,builder.snackbarBtnColor)

    class Builder {
        var activity: Activity? = null
        private set
        var updateType : UpdateType? = null
        private set
        var snackBarMessage : String? = null
        private set
        var snackbarBtnColor : Int = Color.YELLOW
        private set
        var updateListener : UpdateListener? = null
        private set

        fun setActivity(activity: Activity) = apply {  this.activity = activity }
        fun setUpdateType(updateType: UpdateType) = apply { this.updateType = updateType }
        fun setSnackBarMessage(snackBarMessage : String) = apply { this.snackBarMessage = snackBarMessage }
        fun setSnackbarBtnColor(snackbarBtnColor : Int) = apply { this.snackbarBtnColor = snackbarBtnColor }
        fun build() = this
        fun create() = UpdateManager(this)
    }

    private var appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(activity)
    private var appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo
    var updateListener: UpdateListener? = null


    private val type = when(updateType){
        UpdateType.FLEXIBLE -> AppUpdateType.FLEXIBLE
        UpdateType.IMMEDIATE -> AppUpdateType.IMMEDIATE
    }

    fun checkUpdate() {
        appUpdateInfoTask
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(type)) {
                    updateListener?.onUpdateChecked(appUpdateInfo,true)
                } else{
                    updateListener?.onUpdateChecked(appUpdateInfo,false)
                }
            }.addOnFailureListener { exception ->
                updateListener?.onUpdateCheckFailure(exception)
            }
    }

    private val listener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED){
            popupSnackbarForCompleteUpdate()
        }
    }

    fun update(appUpdateInfo: AppUpdateInfo){
        if (type == AppUpdateType.FLEXIBLE){
            appUpdateManager.registerListener(listener)
        }
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            type,
            activity,
            UPDATE_REQ_CODE)
    }

    private fun popupSnackbarForCompleteUpdate() {
        val message = snackBarMessage ?: activity.getString(R.string.update_message)
        Snackbar.make(
            activity.window.decorView,
            message,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("재시작") {
                if (type == AppUpdateType.FLEXIBLE){
                    appUpdateManager.unregisterListener(listener)
                }
                appUpdateManager.completeUpdate()
            }
            setActionTextColor(snackbarBtnColor)
            show()
        }
    }



    companion object {
        const val UPDATE_REQ_CODE = 1010
    }

}
