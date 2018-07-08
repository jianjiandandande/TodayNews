package edu.nuc.vincent.com.todaynews.module.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import edu.nuc.vincent.com.todaynews.R;
import es.dmoral.toasty.Toasty;

public class SettingActivity extends AppCompatActivity {

    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.updating)
    RelativeLayout updating;
    @InjectView(R.id.log_off)
    Button logOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);

        SharedPreferences getData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean loginState = getData.getBoolean("loginState", false);
        if (!loginState){
            logOff.setText("立 即 登 录");
        }
    }

    @OnClick({R.id.btn_back, R.id.updating, R.id.log_off})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.updating:

                Toasty.warning(this, "已是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.log_off:

                SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                editor.putBoolean("loginState", false);
                editor.apply();




                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);

                break;
        }
    }
}
