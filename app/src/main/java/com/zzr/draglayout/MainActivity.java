package com.zzr.draglayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.Random;

import drag.DragLayout;
import util.Cheeses;
import util.Utils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        final ListView mLeftList = (ListView) findViewById(R.id.lv_left);
        final ListView mMainList = (ListView) findViewById(R.id.lv_main);
        final ImageView mHeaderImage = (ImageView) findViewById(R.id.iv_header);
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.mll);


        // 查找Draglayout, 设置监听
        DragLayout mDragLayout = (DragLayout) findViewById(R.id.dl);

        // 设置引用
//		mLinearLayout.setDraglayout(mDragLayout);

        mDragLayout.setDragStatusListener(new DragLayout.OnDragStatusChangeListener() {

            @Override
            public void onOpen() {
                Utils.showToast(MainActivity.this, "onOpen");
                // 左面板ListView随机设置一个条目
                Random random = new Random();

                int nextInt = random.nextInt(50);
                mLeftList.smoothScrollToPosition(nextInt);

            }

            @Override
            public void onDraging(float percent) {
                Log.d(TAG, "onDraging: " + percent);// 0 -> 1
                // 更新图标的透明度
                // 1.0 -> 0.0
                ViewHelper.setAlpha(mHeaderImage, 1 - percent);
            }

            @Override
            public void onClose() {
                Utils.showToast(MainActivity.this, "onClose");
                // 让图标晃动
//				mHeaderImage.setTranslationX(translationX)
                ObjectAnimator mAnim = ObjectAnimator.ofFloat(mHeaderImage, "translationX", 15.0f);
                mAnim.setInterpolator(new CycleInterpolator(4));
                mAnim.setDuration(500);
                mAnim.start();
            }
        });

        mLeftList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView mText = ((TextView)view);
                mText.setTextColor(Color.WHITE);
                return view;
            }
        });

        mMainList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.NAMES));
    }
}
