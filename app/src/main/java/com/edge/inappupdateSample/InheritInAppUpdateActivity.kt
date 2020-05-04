package com.edge.inappupdateSample

import android.os.Build
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.edge.inappupdate.InAppUpdateActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.InstallStatus

class InheritInAppUpdateActivity : InAppUpdateActivity() {

    private lateinit var progressBar:ProgressBar
    private lateinit var progressTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inherit_in_app_update)
        initView()
    }

    private fun initView(){
        progressBar = findViewById(R.id.progress_bar)
        progressTv = findViewById(R.id.progress_tv)
    }


    override fun onUpdateAvailable(appUpdateInfo: AppUpdateInfo, updateAvailable: Boolean) {
        if (updateAvailable){
            startUpdate(appUpdateInfo)
        }
    }


    override fun onUpdateState(installState: InstallState, bytesDownLoaded: Long, totalBytesToDownLoaded: Long) {
        when(installState.installStatus()){
            InstallStatus.DOWNLOADING->{
                setProgressPercent(bytesDownLoaded, totalBytesToDownLoaded)
            }
            InstallStatus.DOWNLOADED->{
                restart()
            }
        }
    }


    override fun onUpdateFailure(exception: Exception?) {
        Toast.makeText(this,"업데이트 체크에 실패하였습니다", Toast.LENGTH_SHORT).show()
    }

    private fun setProgressPercent( bytesDownLoaded: Long, totalBytesToDownLoaded: Long){
        val downLoadPercent = bytesDownLoaded/totalBytesToDownLoaded*100
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(downLoadPercent.toInt(),true)
        } else{
            progressBar.progress = downLoadPercent.toInt()
        }
        progressTv.text="$bytesDownLoaded / $totalBytesToDownLoaded"
    }
}
