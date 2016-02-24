package com.gtbit.godebate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class Welcome extends FragmentActivity {

    private ViewPager pager;
    private SmartTabLayout highlighter;
    private ButtonFlat btnnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        pager = (ViewPager) findViewById(R.id.pager);
        highlighter = (SmartTabLayout) findViewById(R.id.highlighter);
        highlighter.setSelectedIndicatorColors(Color.WHITE);
        btnnext = (ButtonFlat) findViewById(R.id.next);

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position){
                    case 0: return new WelcFragment1();
                    case 1: return new WelcFragment2();
                    case 2: return new WelcFragment3();

                    default:return null;

                }


            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        pager.setAdapter(adapter);
        highlighter.setViewPager(pager);


        highlighter.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int i) {
                if(i==0||i==1||i==2) {
                    btnnext.setVisibility(View.VISIBLE);
                }

            }

        });

    }

    public void onNext(View view)
    {
       StartMainActivity();
    }

    private void StartMainActivity()
    {
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        preferences.edit().putBoolean("welcome_complete", true).apply();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();


    }
}
