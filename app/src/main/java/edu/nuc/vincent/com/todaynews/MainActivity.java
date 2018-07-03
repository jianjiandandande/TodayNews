package edu.nuc.vincent.com.todaynews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import java.util.HashMap;
import java.util.Map;

import edu.nuc.vincent.com.todaynews.entity.Result;
import edu.nuc.vincent.com.todaynews.entity.UserInfo;
import edu.nuc.vincent.com.todaynews.module.news.NewsFragment;
import edu.nuc.vincent.com.todaynews.module.smallnew.SmallNewsFragment;
import edu.nuc.vincent.com.todaynews.module.video.VideoFragment;
import edu.nuc.vincent.com.todaynews.utils.Constant;
import edu.nuc.vincent.com.todaynews.utils.HttpHelper;
import edu.nuc.vincent.com.todaynews.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout mContainer;

    private FragmentManager mManager;

    private Retrofit mRetrofit;

    private GetDatas mGetDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = (CoordinatorLayout) findViewById(R.id.fragment_container);

        mManager = getSupportFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();

        initFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRetrofit = new HttpHelper.Builder().baseUrl(Constant.WEB_BASE_URL).build();
        mGetDatas = mRetrofit.create(GetDatas.class);

        SharedPreferences getData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        L.d("icon  url = "+getData.getString("user_icon",""));

        getUserInfo();

    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        final String username = getIntent().getStringExtra("username");
        Map<String,String> map = new HashMap<>();
        map.put("username",username);

        mGetDatas.getUserInfoFromWeb(map).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                if (response.isSuccessful()){

                    UserInfo userInfo = response.body();

                    SharedPreferences.Editor editor = getSharedPreferences("userInfo",MODE_PRIVATE).edit();
                    editor.putBoolean("loginState",true);
                    editor.putString("username",userInfo.getUsername());
                    editor.putString("user_icon",userInfo.getUser_icon());
                    editor.putString("telephone",userInfo.getTelephone());
                    editor.apply();


                }

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * 切换不同的页面
         * @param item
         * @return
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = mManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    Fragment homeFragment = new NewsFragment();
                    transaction.replace(R.id.fragment_container, homeFragment);
                    break;

                case R.id.navigation_video:
                    Fragment videoFragment = new VideoFragment();
                    transaction.replace(R.id.fragment_container, videoFragment);
                    break;
                case R.id.navigation_smallnews:
                    Fragment smallNewsFragment = new SmallNewsFragment();
                    transaction.replace(R.id.fragment_container, smallNewsFragment);
                    break;
                default:
                    return false;
            }
            transaction.commit();
            return true;
        }
    };

    /**
     * 初始化第一个Fragment
     */
    private void initFragment() {
        mManager = getSupportFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment homeFragment = new NewsFragment();
        transaction.replace(R.id.fragment_container, homeFragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
