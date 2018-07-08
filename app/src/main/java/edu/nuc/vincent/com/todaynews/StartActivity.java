package edu.nuc.vincent.com.todaynews;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    private boolean isFirstUse = true;

    private static final int MY_PERMISSION_REQUEST_CODE = 1;

    private String[] Permissions = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.BODY_SENSORS
    };

    private Thread mThread;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            try {
                mThread.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent it = new Intent();
            it.setClass(StartActivity.this, MainActivity.class);//第一个参数为当前Activity，第二个为将要跳转的Activity
            StartActivity.this.startActivity(it);
            StartActivity.this.finish();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);



        isFirstUse = checkIsFirstUse();

        if (isFirstUse) {

            requirePermission();

        } else {
            boolean isAllGranted = checkPermissionAllGranted(Permissions);

            if (isAllGranted) {
                intoApp();
            }
        }

    }

    /**
     * 检测是否第一次使用
     *
     * @return
     */
    private boolean checkIsFirstUse() {
        return true;
    }

    /**
     * 检测权限
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 权限申请
     */
    private void requirePermission() {

        ActivityCompat.requestPermissions(
                this, Permissions,
                MY_PERMISSION_REQUEST_CODE
        );

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {

                intoApp();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }


    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您有权限未授予，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }


    /**
     * 进入APP
     */
    private void intoApp() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
                handler.sendEmptyMessage(0);
            }
        });
        mThread.start();
    }

}
