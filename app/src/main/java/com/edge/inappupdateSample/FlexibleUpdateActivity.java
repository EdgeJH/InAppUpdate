package com.edge.inappupdateSample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.edge.inappupdate.UpdateListener;
import com.edge.inappupdate.UpdateManager;
import com.edge.inappupdate.UpdateType;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.model.InstallStatus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlexibleUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible);
         UpdateManager.Builder builder = new UpdateManager.Builder()
                .setActivity(this)
                .setUpdateType(UpdateType.FLEXIBLE);
         final UpdateManager updateManager = builder.create();
         updateManager.setUpdateListener(new UpdateListener() {
             @Override
             public void onUpdateChecked(@NotNull AppUpdateInfo appUpdateInfo, boolean updateAvailable) {
                 if (updateAvailable){
                     updateManager.update(appUpdateInfo);
                 }

             }
             @Override
             public void onUpdateCheckFailure(@Nullable Exception exception) {
                 if (exception!=null){
                     Log.d("abcd", "error : " +  exception.getMessage());
                 }
             }
             @Override
             public void onUpdateState(@NotNull InstallState installState, long bytesDownLoaded, long totalBytesToDownLoaded) {
                 if (installState.installStatus()== InstallStatus.DOWNLOADED){
                     updateManager.showSnackBarForCompleteUpdate("업데이트가 완료 되었습니다",ContextCompat.getColor(FlexibleUpdateActivity.this,R.color.colorAccent));
                 }
             }
         });
        updateManager.checkUpdate();
    }
}
