package com.example.bookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, appName, splashImg;
    LottieAnimationView mLottieAnimationView;
    private static final int NUM_PAGES = 3;
    private ViewPager mViewPager;
    private ScreenSliderPagerAdapter pagerAdapter;
    Animation anim;
    private Button skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        splashImg = findViewById(R.id.splashImg);
        logo = findViewById(R.id.bookLogo);
        appName = findViewById(R.id.appName);
        mLottieAnimationView = findViewById(R.id.lottie);

        mViewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);

        anim = AnimationUtils.loadAnimation(this, R.anim.o_b_anim);
        mViewPager.startAnimation(anim);

        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        mLottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

    }

    private class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSliderPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}