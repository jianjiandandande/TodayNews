package edu.nuc.vincent.com.todaynews.module.mine;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.L;
import edu.nuc.vincent.com.todaynews.view.CustomDialog;

public class MineActivity extends AppCompatActivity {

    @InjectView(R.id.mine_back)
    ImageView mineBack;
    @InjectView(R.id.dynamic_count)
    TextView dynamicCount;
    @InjectView(R.id.mine_dynamic)
    LinearLayout mineDynamic;
    @InjectView(R.id.attention_count)
    TextView attentionCount;
    @InjectView(R.id.mine_attention)
    LinearLayout mineAttention;
    @InjectView(R.id.fans_count)
    TextView fansCount;
    @InjectView(R.id.mine_fans)
    LinearLayout mineFans;
    @InjectView(R.id.like)
    LinearLayout like;
    @InjectView(R.id.history)
    LinearLayout history;
    @InjectView(R.id.feedback)
    RelativeLayout feedback;
    @InjectView(R.id.setting)
    RelativeLayout setting;
    @InjectView(R.id.mine_icon)
    CircleImageView mineIcon;
    @InjectView(R.id.mine_username)
    TextView mineUsername;

    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    public static final int SHOW_PHOTO = 2;
    public static final int SHOW_PHOTO_ALBUM = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.inject(this);

        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        SharedPreferences getData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean loginState = getData.getBoolean("loginState", false);

        if (loginState == true) {

            String username = getData.getString("username", "");
            mineUsername.setText(username);
            mineUsername.setClickable(false);
            String userIconUrl = getData.getString("user_icon", "");


            if (!userIconUrl.equals("")) {
                Glide.with(this).load(userIconUrl).into(mineIcon);
            }
        }else {
            mineIcon.setImageDrawable(getDrawable(R.mipmap.ic_launcher));

            mineUsername.setText("点击登录");
            mineUsername.setClickable(true);
            mineUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MineActivity.this,LoginActivity.class));
                }
            });
        }

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCamera();
            }
        });
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPicture();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    @OnClick({R.id.mine_back, R.id.mine_icon, R.id.mine_dynamic, R.id.mine_attention,
            R.id.mine_fans, R.id.like, R.id.history, R.id.feedback, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_back:
                finish();
                break;
            case R.id.mine_icon:
                dialog.show();
                break;
            case R.id.mine_dynamic:
                break;
            case R.id.mine_attention:
                Intent intent = new Intent(MineActivity.this,AttentionActivity.class);
                startActivity(intent);

                break;
            case R.id.mine_fans:
                break;
            case R.id.like:
                startActivity(new Intent(this,CollectionActivity.class));
                break;
            case R.id.history:

                startActivity(new Intent(this,HistoryActivity.class));
                break;
            case R.id.feedback:
                break;
            case R.id.setting:
                Intent attentionIntent = new Intent(this, SettingActivity.class);
                startActivity(attentionIntent);
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }


    private Uri imageUri = null;
    private Uri uri;//裁剪万照片保存地址


    //跳转相机
    private void toCamera() {
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(MineActivity.this, "edu.nuc.vincent.com.todaynews.fileProvider", outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constant.CAMERA_REQUEST_CODE);
        dialog.dismiss();

    }

    //跳转图库
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Constant.IMAGE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    // 启动裁剪
                    startActivityForResult(intent, SHOW_PHOTO);
                }
                break;
            case SHOW_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将裁剪后的照片显示出来
                        Glide.with(this).load(imageUri).into(mineIcon);
                        updateSharedPreferences();
                        updateService();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constant.IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    // 启动裁剪
                    startActivityForResult(intent, SHOW_PHOTO_ALBUM);
                }
                break;
            case SHOW_PHOTO_ALBUM:
                if (resultCode == RESULT_OK) {
                    try {
                        Glide.with(this).load(imageUri).into(mineIcon);
                        updateSharedPreferences();
                        updateService();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更新SharedPreferences中的头像
     */
    private void updateSharedPreferences(){
        SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
        editor.putString("user_icon", imageUri.toString());
        editor.apply();
    }

    /**
     * 更新服务端
     */
    private void updateService(){

    }
}
