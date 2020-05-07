package com.edge.inappupdate

import android.app.Activity
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallState

abstract class InAppUpdateActivity : AppCompatActivity() {

    private lateinit var updateManager : UpdateManager


    private fun initializeUpdateManager(activity: Activity){
        val builder =
            UpdateManager.Builder()
                .setActivity(activity)
                .setUpdateType(UpdateType.FLEXIBLE)
        updateManager = builder.create()
        updateManager.updateListener = object : UpdateListener {
            override fun onUpdateChecked(appUpdateInfo: AppUpdateInfo, updateAvailable: Boolean) {
                onUpdateAvailable(appUpdateInfo,updateAvailable)
            }

            override fun onUpdateCheckFailure(exception: Exception?) {
                onUpdateFailure(exception)
            }

            override fun onUpdateState(installState: InstallState, bytesDownLoaded: Long, totalBytesToDownLoaded: Long) {
                onInstallState(installState, bytesDownLoaded, totalBytesToDownLoaded)
            }
        }
    }

    fun checkUpdate(activity: Activity){
        initializeUpdateManager(activity)
        updateManager.checkUpdate()
    }

    /**
     * 업데이트를 시작하는 기능입니다.
     *
     * @param appUpdateInfo 플레이스토어의 각종 업데이트 정보들이 들어있습니다. 업데이트를 위한 필수 파라미터입니다.
     * @author edgejhdev
     * @version 1.1.0
     * @see None
     */
    protected fun startUpdate(appUpdateInfo: AppUpdateInfo){
        updateManager.update(appUpdateInfo)
    }

    /**
     * 업데이트 완료 후 재시작을 요청하는 기능입니다.
     *
     * @author edgejhdev
     * @version 1.1.0
     * @see None
     */
    protected fun restart(){
        updateManager.completeUpdate()
    }
    /**
     * 업데이트 완료 후 재시작을 요청하는 스낵바를 띄우는 기능입니다.
     *
     * @param colorRes 스낵바의 재시작 버튼의 컬러 값 입니다.
     * @param updateMessage 스낵바의 메세지 값 입니다.
     * @author edgejhdev
     * @version 1.1.0
     * @see None
     */
    protected fun showRestartSnackBar(@ColorRes colorRes : Int , updateMessage : String){
        updateManager.showSnackBarForCompleteUpdate(updateMessage,ContextCompat.getColor(this, colorRes))
    }

    /**
     * 업데이트 중 상태 관련 이벤트 리스너 입니다.
     *
     * @param installState 업데이트관련 상태 값들입니다. (다운로드중,다운로드 완료 등등..)
     * @param bytesDownLoaded 업데이트 중 현재까지 다운로드된 바이트 수 입니다.
     * @param totalBytesToDownLoaded 업데이트 완료까지 총 다운받아야할 바이트 수 입니다.
     * @author edgejhdev
     * @version 1.1.0
     * @see None
     */
    abstract fun onInstallState(installState: InstallState, bytesDownLoaded: Long, totalBytesToDownLoaded: Long)

    /**
     * 업데이트 가능 여부 콜백 메소드 입니다.
     *
     * @param appUpdateInfo 플레이스토어의 각종 업데이트 정보들이 들어있습니다.
     * @param updateAvailable 업데이트 가능여부 입니다.
     * @author edgejhdev
     * @version 1.1.0
     * @see None
     */
    abstract fun onUpdateAvailable(appUpdateInfo: AppUpdateInfo,updateAvailable: Boolean)

    /**
     * 업데이트 체크 실행 후 구글의 각종 예외 현상들을 받는 이벤트 리스너 입니다.
     *
     * @param exception 업데이트 체크 실행 후 구글의 각종 예외 현상입니다.
     * @author edgejhdev
     * @version 1.1.0
     * @see None
     */
    abstract fun onUpdateFailure(exception: Exception?)
}
