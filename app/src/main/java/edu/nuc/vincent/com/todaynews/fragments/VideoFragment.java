package edu.nuc.vincent.com.todaynews.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.nuc.vincent.com.todaynews.R;
import edu.nuc.vincent.com.todaynews.adapter.MyFragmentAdapter;

/**
 * Created by Vincent on 2018/6/20.
 */

public class VideoFragment extends Fragment {

    private ViewPager mViewPager;

    private List<Fragment> mFragments;

    private TextView mRecommend,mMusic,mMovie,mSocial,mGame,mCate,mPopulation;

    private List<TextView> mTabs ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_video,container,false);

        initTextView(view);

        initViewPager(view);

        return view;
    }

    /**
     * 初始化ViewPager
     * @param view
     */
    private void initViewPager(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.video_viewPager);
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTabs.size(); i++) {

            Fragment fragment = VideoTabFragment.newInstance(mTabs.get(i).getText().toString());

            mFragments.add(fragment);

        }

        mViewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), mFragments));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new myOnPageChangeListener());

    }

    /**
     * 初始化TextView
     * @param view
     */
    private void initTextView(View view) {

        mRecommend = (TextView) view.findViewById(R.id.video_tab_recommend);
        mMusic = (TextView) view.findViewById(R.id.video_tab_music);
        mMovie = (TextView) view.findViewById(R.id.video_tab_movie);
        mSocial = (TextView) view.findViewById(R.id.video_tab_social);
        mGame = (TextView) view.findViewById(R.id.video_tab_game);
        mCate = (TextView) view.findViewById(R.id.video_tab_cate);
        mPopulation = (TextView) view.findViewById(R.id.video_tab_popular);

        mTabs = new ArrayList<>();
        mTabs.add(mRecommend);
        mTabs.add(mMusic);
        mTabs.add(mMovie);
        mTabs.add(mSocial);
        mTabs.add(mGame);
        mTabs.add(mCate);
        mTabs.add(mPopulation);

        mRecommend.setOnClickListener(new TextListener(0));
        mMusic.setOnClickListener(new TextListener(1));
        mMovie.setOnClickListener(new TextListener(2));
        mSocial.setOnClickListener(new TextListener(3));
        mGame.setOnClickListener(new TextListener(4));
        mCate.setOnClickListener(new TextListener(5));
        mPopulation.setOnClickListener(new TextListener(6));

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
     *///mRecommend,mMusic,mMovie,mSocial,mGame,mCate,mPopulation
    private void resetTextViewColor() {
        mRecommend.setTextColor(Color.BLACK);
        mMusic.setTextColor(Color.BLACK);
        mMovie.setTextColor(Color.BLACK);
        mSocial.setTextColor(Color.BLACK);
        mGame.setTextColor(Color.BLACK);
        mCate.setTextColor(Color.BLACK);
        mPopulation.setTextColor(Color.BLACK);

    }

}
