package com.maoheni.mao.zhbj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {
    private RelativeLayout sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = (RelativeLayout) findViewById(R.id.activity_splash);
        startAnimation();
    }

    public void startAnimation(){
        AnimationSet set = new AnimationSet(false);
        //rotateAnimation
        RotateAnimation rotateAnimation= new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        //keep state
        rotateAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                jumpToNextPage();
            }
        });

        sp.startAnimation(set);
    }

    private void jumpToNextPage(){
        boolean userGuide = PreferenceUtil.getBoolean(this,"user_guide",false);
        if(!userGuide){
            startActivity(SplashActivity.this,UserGuideActivity.class);
        }else{
            startActivity(SplashActivity.this,MainActivity.class);
        }
    }
}
