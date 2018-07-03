package edu.nuc.vincent.com.todaynews.module.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.nuc.vincent.com.todaynews.module.mine.MineActivity;
import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.SearchActivity;
import edu.nuc.vincent.com.todaynews.adapter.MyFragmentAdapter;
import edu.nuc.vincent.com.todaynews.utils.L;

/**
 * Created by Vincent on 2018/6/20.
 */

public class NewsFragment extends Fragment {

    @InjectView(R.id.user_icon)
    CircleImageView userIcon;
    @InjectView(R.id.home_search)
    TextView homeSearch;
    @InjectView(R.id.send_news)
    ImageView sendNews;
    @InjectView(R.id.home_more_option)
    ImageView homeMoreOption;
    private ViewPager mViewPager;

    private List<Fragment> mFragments;

    private TextView mAttention, mRecommend, mHotspot, mVideo, mWorldCup, mCate;

    private List<TextView> mTabs;

    private String[] mTabname = {"news_tech", "news_game", "movie", "news_history", "news_sports", "news_food"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_home, container, false);
        ButterKnife.inject(this, view);
        SharedPreferences getData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean loginState = getData.getBoolean("loginState",false);

        if (loginState==true){
            String userIconUrl = getData.getString("user_icon","");

            if (!userIconUrl.equals("")) {
                L.d("icon_url = "+userIconUrl);
                Glide.with(getContext()).load(userIconUrl).into(userIcon);
            }
        }

        initTextView(view);

        initViewPager(view);


        return view;
    }

    /**
     * 初始化ViewPager
     *
     * @param view
     */
    private void initViewPager(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.home_viewPager);
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTabs.size(); i++) {

            Fragment fragment = NewsTabFragment.newInstance(mTabname[i]);

            mFragments.add(fragment);

        }
        mViewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), mFragments));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new myOnPageChangeListener());

    }

    /**
     * 初始化TextView
     *
     * @param view
     */
    private void initTextView(View view) {

        mAttention = (TextView) view.findViewById(R.id.home_tab_attention);
        mRecommend = (TextView) view.findViewById(R.id.home_tab_recommend);
        mHotspot = (TextView) view.findViewById(R.id.home_tab_hotspot);
        mVideo = (TextView) view.findViewById(R.id.home_tab_video);
        mWorldCup = (TextView) view.findViewById(R.id.home_tab_World_Cup);
        mCate = (TextView) view.findViewById(R.id.home_tab_cate);

        mTabs = new ArrayList<>();
        mTabs.add(mAttention);
        mTabs.add(mRecommend);
        mTabs.add(mHotspot);
        mTabs.add(mVideo);
        mTabs.add(mWorldCup);
        mTabs.add(mCate);

        mAttention.setOnClickListener(new TextListener(0));
        mRecommend.setOnClickListener(new TextListener(1));
        mHotspot.setOnClickListener(new TextListener(2));
        mVideo.setOnClickListener(new TextListener(3));
        mWorldCup.setOnClickListener(new TextListener(4));
        mCate.setOnClickListener(new TextListener(5));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.user_icon, R.id.home_search, R.id.send_news, R.id.home_more_option})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_icon:
                Intent intent = new Intent(getContext(), MineActivity.class);
                startActivity(intent);
                break;
            case R.id.home_search:

                homeSearch.setFocusable(false);;
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.send_news:

                break;
            case R.id.home_more_option:
                break;
        }
    }


    /**
     * 内部类 重写TextView点击事件
     */
    public class TextListener implements View.OnClickListener {
        private int index = 0;

        public TextListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    public class myOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int position) {
            resetTextViewColor();
            mTabs.get(position).setTextColor(Color.parseColor("#f75959"));

        }
    }

    /**
     * 重置文字的颜色
     */
    private void resetTextViewColor() {
        mAttention.setTextColor(Color.BLACK);
        mRecommend.setTextColor(Color.BLACK);
        mHotspot.setTextColor(Color.BLACK);
        mVideo.setTextColor(Color.BLACK);
        mWorldCup.setTextColor(Color.BLACK);
        mCate.setTextColor(Color.BLACK);

    }

}
