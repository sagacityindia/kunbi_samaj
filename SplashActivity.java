package com.sagacity.kunbhi_samaj.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sagacity.kunbhi_samaj.Constants;
import com.sagacity.kunbhi_samaj.R;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_DURATION = 4000;

    private ObjectAnimator waveOneAnimator;
    private ObjectAnimator waveTwoAnimator;
    private ObjectAnimator waveThreeAnimator;
    private TextView hangoutTvOne;
    private TextView hangoutTvTwo;
    private TextView hangoutTvThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Log.v("Splash Screen","Check 1");
        Constants.ctx=this;
        startAnimation();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
// then you use
//        M=prefs.getString("LANG", "en");

        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(prefs.getString("LANG", "en"));
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_DURATION);
    }
    private void startAnimation() {
        // ********* hangout ************
        hangoutTvOne = (TextView) findViewById(R.id.hangoutTvOne);
        hangoutTvTwo = (TextView) findViewById(R.id.hangoutTvTwo);
        hangoutTvThree = (TextView) findViewById(R.id.hangoutTvThree);
        waveAnimation();

        // --------------------------------------
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressWarnings("static-access")
    public void waveAnimation() {
        PropertyValuesHolder tvOne_Y = PropertyValuesHolder.ofFloat(
                hangoutTvOne.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvOne_X = PropertyValuesHolder.ofFloat(
                hangoutTvOne.TRANSLATION_X, 0);
        waveOneAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvOne,
                tvOne_X, tvOne_Y);
        waveOneAnimator.setRepeatCount(-1);
        waveOneAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveOneAnimator.setDuration(300);
        waveOneAnimator.start();

        PropertyValuesHolder tvTwo_Y = PropertyValuesHolder.ofFloat(
                hangoutTvTwo.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvTwo_X = PropertyValuesHolder.ofFloat(
                hangoutTvTwo.TRANSLATION_X, 0);
        waveTwoAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvTwo,
                tvTwo_X, tvTwo_Y);
        waveTwoAnimator.setRepeatCount(-1);
        waveTwoAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveTwoAnimator.setDuration(300);
        waveTwoAnimator.setStartDelay(100);
        waveTwoAnimator.start();

        PropertyValuesHolder tvThree_Y = PropertyValuesHolder.ofFloat(
                hangoutTvThree.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvThree_X = PropertyValuesHolder.ofFloat(
                hangoutTvThree.TRANSLATION_X, 0);
        waveThreeAnimator = ObjectAnimator.ofPropertyValuesHolder(
                hangoutTvThree, tvThree_X, tvThree_Y);
        waveThreeAnimator.setRepeatCount(-1);
        waveThreeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveThreeAnimator.setDuration(300);
        waveThreeAnimator.setStartDelay(200);
        waveThreeAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (waveOneAnimator != null && waveTwoAnimator != null
                && waveThreeAnimator != null) {
            waveOneAnimator.cancel();
            waveOneAnimator.removeAllListeners();

            waveTwoAnimator.cancel();
            waveTwoAnimator.removeAllListeners();

            waveThreeAnimator.cancel();
            waveThreeAnimator.removeAllListeners();
        }
    }
}
