package com.maoheni.mao.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.maoheni.mao.zhbj.util.PreferenceUtil;

import java.util.ArrayList;

public class UserGuideActivity extends AppCompatActivity {
    private static final int[] mImageIds = new int[] { R.mipmap.guide_1,
            R.mipmap.guide_2, R.mipmap.guide_3 };
    private ViewPager vpGuide;
    private LinearLayout llPointGroup;
    private Button btnStart;
    private ArrayList<ImageView> mImageViewList;
    private int mPointWidth;// 圆点间的距离
    private View viewRedPoint;// 小红点


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_guide);
        vpGuide = (ViewPager)findViewById(R.id.vp_guide);
        llPointGroup =(LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint = findViewById(R.id.view_red_point);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.setBoolean(UserGuideActivity.this,"is_user_guide_showed",true);
                startActivity(new Intent(UserGuideActivity.this,MainActivity.class));
                finish();
            }
        });

        initViews();
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.addOnPageChangeListener(new GuidePagerListener());
        //vpGuide.setAdapter(new GuideAdapter());
    }

    private void initViews(){
        mImageViewList = new ArrayList<ImageView>();
        for(int i =0; i < mImageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(imageView);
        }

        for (int i = 0; i<mImageIds.length; i++){
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10,10);
            if(i>0){
                layoutParams.leftMargin = 10;
            }
            point.setLayoutParams(layoutParams );
            llPointGroup.addView(point);
        }

        // 获取视图树, 对layout结束事件进行监听
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // 当layout执行结束后回调此方法
                    @Override
                    public void onGlobalLayout() {
                        System.out.println("layout 结束");
                        llPointGroup.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        mPointWidth = llPointGroup.getChildAt(1).getLeft()
                                - llPointGroup.getChildAt(0).getLeft();
                        System.out.println("圆点距离:" + mPointWidth);
                    }
                });



    }

    class GuideAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
            //return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //mImageViewList.remove(position);
            container.removeView((View) object);
            //super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class GuidePagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // System.out.println("当前位置:" + position + ";百分比:" + positionOffset
            // + ";移动距离:" + positionOffsetPixels);
            int len = (int) (mPointWidth * positionOffset) + position
                    * mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint
                    .getLayoutParams();// 获取当前红点的布局参数
            params.leftMargin = len;// 设置左边距

            viewRedPoint.setLayoutParams(params);// 重新给小红点设置布局参数
        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {// 最后一个页面
                btnStart.setVisibility(View.VISIBLE);// 显示开始体验的按钮
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
