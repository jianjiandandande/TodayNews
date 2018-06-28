package edu.nuc.vincent.com.todaynews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import edu.nuc.vincent.com.todaynews.module.home.HomeFragment;
import edu.nuc.vincent.com.todaynews.module.smallnew.SmallNewsFragment;
import edu.nuc.vincent.com.todaynews.module.video.VideoFragment;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout mContainer;

    private FragmentManager mManager;


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

                    Fragment homeFragment = new HomeFragment();
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


        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
    }

    /**
     * 初始化第一个Fragment
     */
    private void initFragment() {
        mManager = getSupportFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment homeFragment = new HomeFragment();
        transaction.replace(R.id.fragment_container, homeFragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
