# Android InAppUpdate

안드로이드 어플리케이션의 버전 관리를 위하여 구글에서 제공하는 In-App-Support를 좀 더 사용하기 쉽도록 만든 라이브러리 입니다.


## Download

``` gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
      implementation 'com.google.android.play:core:1.6.3'
	    implementation 'com.github.EdgeJH:InAppUpdate:Tag'
	}

```



## Usage

#### Flexible Update

``` java

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible);

         UpdateManager.Builder builder = new UpdateManager.Builder()
                .setActivity(this)
                .setUpdateType(UpdateType.FLEXIBLE)
                .setSnackBarMessage("업데이트가 완료 되었습니다")
                .setSnackbarBtnColor(ContextCompat.getColor(this,R.color.colorAccent))
                .build();
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

```


#### Immediate Update


``` java
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




```

