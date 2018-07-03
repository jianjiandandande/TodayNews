package edu.nuc.vincent.com.todaynews.module.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lieweisi.loadinglib.LoadingDialog;
import com.lieweisi.loadinglib.LoadingUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import edu.nuc.vincent.com.todaynews.GetDatas;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.entity.Result;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.edit_register_username)
    EditText editRegisterUsername;
    @InjectView(R.id.edit_register_password)
    EditText editRegisterPassword;
    @InjectView(R.id.edit_register_telephone)
    EditText editRegisterTelephone;
    @InjectView(R.id.btn_register)
    Button btnRegister;

    private Retrofit mRetrofit;
    private GetDatas mGetDatas;

    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        editRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((!editRegisterPassword.getText().toString().equals(""))&&(!editRegisterUsername.getText().toString().equals(""))){
                    btnRegister.setBackgroundColor(Color.parseColor("#f75959"));
                }
            }
        });

        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(6, TimeUnit.SECONDS).
                readTimeout(6, TimeUnit.SECONDS).
                writeTimeout(6, TimeUnit.SECONDS).build();


        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://10.0.161.115:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetDatas = mRetrofit.create(GetDatas.class);

    }

    @OnClick({R.id.btn_back, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:

                finish();

                break;
            case R.id.btn_register:

                register();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        dialog = LoadingUtil.show(dialog,this,LoadingUtil.TYPE_1);
        dialog.setCancelable(true);
        Map<String,String> map = new HashMap<>();
        map.put("username",editRegisterUsername.getText().toString());
        map.put("password",editRegisterPassword.getText().toString());
        map.put("telephone",editRegisterTelephone.getText().toString());
        map.put("user_icon","");

        mGetDatas.checkRegister(map).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                dialog.dismiss();
                if (response.isSuccessful()){
                    Result result = response.body();
                    if (result.getCode().equals(Constant.REGISTER_SUCCESS_CODE)){
                        Toasty.success(RegisterActivity.this,result.getMsg(),Toast.LENGTH_SHORT,true).show();
                        finish();
                    }else {
                        Toasty.error(RegisterActivity.this,result.getMsg(),Toast.LENGTH_SHORT,true).show();
                    }

                }

            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dialog.dismiss();
                Toasty.warning(RegisterActivity.this,"网络状态异常",Toast.LENGTH_SHORT,true).show();
            }
        });
    }
}
