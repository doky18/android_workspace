package com.example.photoapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();
    
    ActivityResultLauncher launcher;            //권한 요청 객체
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_internal = findViewById(R.id.bt_internal);
        Button bt_external = findViewById(R.id.bt_external);
        
        bt_internal.setOnClickListener((v)->{
            openInternal();
        });
        
        bt_external.setOnClickListener((v)->{
           openExternal();
        });
        
        /*안드로이드의 새로운 권한 정책으로 인하여, 앱의 시작과 동시에 사용자로부터
            안드로이드에서 새로운 정책을 구현하기 위해서 우리는 사용자에게 권한을 요청하고 수락을 받아야 하는데
            이때 사용되는 객체가 바로 ActivityResultLauncher라고 하며,
            이 객체의 인스턴스를 생성하기 위한 메서드는 registerForActivityResult()라 한다

            매개변수 1) 어떤 퍼미션을 요청했는지에 대한 정보
                            ActivityResultContracts
            매개변수 2) 사용자가 해당 요청에 대해 어떤 반응을 보였는지에 대한 정보를 처리할 콜백메서드
            */


        launcher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Log.d(TAG, "요청에 대한 사용자의 반응 결과는 " + result);

                Iterator<String> it = result.keySet().iterator();         //맵에 들어있는 키값만을 일렬로

                while(it.hasNext()){        //키의 수만큼,,
                    String permission_name =it.next();      //권한명을 반환받음..

                    //키(권한명)를 이용하여 맵의 실제 데이터 접근
                    boolean granted = result.get(permission_name);
                    if(granted==false){     //해당 권한에 대해 수락을 안 한 경우라면
                        //일단 거부를 1번 이상하면, 수락할 의도가 없으므로 아래의 메시지를 무조건 수행하면 안된다..
                        //즉 1번만 나와야 한다..
                        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission_name)) {
                            Toast.makeText(MainActivity.this, "권한을 수락해야 이용이 가능합니다", Toast.LENGTH_SHORT).show();
                        }else{
                            //수락을 2회 이상 거절한 경우...
                            Toast.makeText(MainActivity.this, "정상적인 앱 이용을 위해서는 앱 설정에서 권한을 수락해주세요", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
           }
        });

        //권한에 대한 확인 및 수락을 받도록 하자 (단, 마시멜로 이전 폰의 경우엔 허락 불피요)
        if(checkVersion()) {
            //최신 핸드폰이므로, 파일에 대한 접근보다는 사용자로부터 허락을 먼저 받아야 한다
            if(checkGranted()){
                openExternal();
            }else {
                //권한 요청을 시도
                launcher.launch(new String[]{
                        //권한 요청을 시도 (사용자에게는 권한 수락에 대한 팝업이 보이게 된다)
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                });
            }
        }else{
            //구버전 핸드폰이므로 허락이고 뭐고 필요없이 그냥 파일 접근을 하자
        }
    }

    public boolean checkVersion(){
        //마시멜로 폰 부터 새로운 정책을 적용해야하므로,
        //현재 사용자의 폰이 어떤 버전인지를 파악하자
        Log.d(TAG, "SDK_INT는 "+ Build.VERSION.SDK_INT);      //sdk 개발 도구들이 들어가있음
        Log.d(TAG, "VERSION_CODES 마시멜로는"+ Build.VERSION_CODES.M);

        //
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            return true;
        }else {
            return false;
        }
    }

    //사용자로부터 권한 수락을 요청하는 메서드
    public boolean checkGranted(){
       //이미 해당 유저가 권한을 수락했는지 여부를 확인해보자
        int read_permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //읽기 권한을 수락했다면..
        boolean result1 = read_permission == PackageManager.PERMISSION_GRANTED;
        //쓰기 권한을 수락했다면..
        boolean result2 = write_permission == PackageManager.PERMISSION_GRANTED;

        return result1 && result2;
       /* launcher.launch(new String[]{
           Manifest.permission.READ_EXTERNAL_STORAGE,
           Manifest.permission.WRITE_EXTERNAL_STORAGE
        });*/
    }

    /*안드로이드의 저장소 (storage)는 크게 2가지 유형으로 나뉜다
    * 1) 내부저장소 - 외부저장소 중, 해당 앱만이 전용으로 사용하는 저장소를 가리켜 내부저장소라고 한다
    *                      해당앱이 폰에서 설치, 삭제되면 저장소도 함께 삭제되어 진다
    *
    * 2) 외부저장소 - */

    public void openInternal() {
        //내부저장소 root
        File file = new File(this.getFilesDir(), "");
        Log.d(TAG, file.getAbsolutePath());
    }

    public void openExternal() {
        File storage = Environment.getExternalStorageDirectory();
        Log.d(TAG, "외부저장소 경로"+ storage.getAbsolutePath());

        //외부저장소의 하위 디렉토리 및 모든 파일의 목록을 조회해보자
        File[] files = storage.listFiles();                                                  //외부저장소 경로/storage/emulated/0
        Log.d(TAG, "하위 디렉토리 및 파일 수는"+ files.length);            //하위 디렉토리 및 파일 수는12
        for(File file : files){
            Log.d(TAG, file.getName());
        }
    }
}
