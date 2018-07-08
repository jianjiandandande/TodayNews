package edu.nuc.vincent.com.todaynews.module.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import edu.nuc.vincent.com.todaynews.MainActivity;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.entity.Result;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.HttpHelper;
import edu.nuc.vincent.com.todaynews.utils.L;
import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    @InjectView(R.id.edit_username)
    EditText editUsername;
    @InjectView(R.id.edit_password)
    EditText editPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.tv_register)
    TextView tvRegister;

    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    private LoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((!editPassword.getText().toString().equals(""))&&(!editUsername.getText().toString().equals(""))){
                    btnLogin.setBackgroundColor(Color.parseColor("#f75959"));
                }
            }
        });



        mRetrofit = new HttpHelper.Builder().baseUrl(Constant.WEB_BASE_URL).build();

        mGetDatas = mRetrofit.create(GetDatas.class);
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                checkLogin();

                break;
            case R.id.tv_register:

                resgister();
                break;
        }
    }

    /**
     * 注册
     */
    private void resgister() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录校验
     */
    private void checkLogin() {

        final String username = editUsername.getText().toString();

        String password = editPassword.getText().toString();

        dialog = LoadingUtil.show(dialog,this,LoadingUtil.TYPE_1);
        dialog.setCancelable(true);
        Map<String,String> map = new HashMap<>();

        map.put("username",username);
        map.put("password",password);

        mGetDatas.checkLogin(map).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (response.isSuccessful()){

                    L.d(result.getCode()+"check result");

                    if (result.getCode().equals(Constant.LOGIN_SUCCESS_CODE)){

                        Toasty.success(LoginActivity.this,result.getMsg(),
                                Toast.LENGTH_SHORT,true).show();
                        dialog.setMessage("登陆成功");

                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finish();

                    }else {

                        Toasty.error(LoginActivity.this,result.getMsg(),
                                Toast.LENGTH_SHORT,true).show();
                        dialog.setCancelable(true);
                        dialog.dismiss();
                    }
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toasty.warning(LoginActivity.this,"网络状态异常",
                        Toast.LENGTH_SHORT,true).show();
            }
        });


    }
}
