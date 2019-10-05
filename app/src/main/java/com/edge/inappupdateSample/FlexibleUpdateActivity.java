package com.edge.inappupdateSample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.edge.inappupdate.UpdateListener;
import com.edge.inappupdate.UpdateManager;
import com.edge.inappupdate.UpdateType;
import com.google.android.play.core.appupdate.AppUpdateInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlexibleUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible);

         UpdateManager.Builder builder = new UpdateManager.Builder()
                .setActivity(this)
                .setUpdateType(UpdateType.FLEXIBLE)
                .setSnackBarMessage("업데이트가 완료 되었습니다")
                .setSnackbarBtnColor(ContextCompat.getColor(this,R.color.colorAccent));
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
                     Toast.makeText(FlexibleUpdateActivity.this, "error : " +  exception.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }
         });
        updateManager.checkUpdate();
    }
}
