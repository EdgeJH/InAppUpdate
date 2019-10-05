package com.edge.inappupdateSample;

import android.content.Intent;
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

public class ImmediateUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediate_update);
        UpdateManager.Builder builder = new UpdateManager.Builder()
                .setActivity(this)
                .setUpdateType(UpdateType.IMMEDIATE)
                .setSnackBarMessage("업데이트가 완료 되었습니다")
                .setSnackbarBtnColor(ContextCompat.getColor(this, R.color.colorAccent))
                .build();
        final UpdateManager updateManager = builder.create();
        updateManager.setUpdateListener(new UpdateListener() {
            @Override
            public void onUpdateChecked(@NotNull AppUpdateInfo appUpdateInfo, boolean updateAvailable) {
                updateManager.update(appUpdateInfo);
            }

            @Override
            public void onUpdateCheckFailure(@Nullable Exception exception) {
                if (exception != null) {
                    Toast.makeText(ImmediateUpdateActivity.this, "error : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateManager.checkUpdate();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UpdateManager.UPDATE_REQ_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    //업데이트 완료
                    break;
                case RESULT_CANCELED:
                    //업데이트 취소
                    break;
                default:
                    //업데이트 실패
                    break;
            }
        }
    }
}
