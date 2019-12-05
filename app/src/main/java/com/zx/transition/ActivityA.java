package com.zx.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.zx.Utils;
import com.zx.customview.R;
import com.zx.customview.RandTextView;
import com.zx.customview.TagLayout;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @description：
 * @author：bux on 2019/11/29 19:19
 * @email: 471025316@qq.com
 */
public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_layout);
        //setupWindowAnimations();

        final TagLayout tagLayout = findViewById(R.id.tagLayout);

        int childCount = new Random().nextInt(50);
        tagLayout.removeAllViews();
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = Utils.dp2px(12);
        params.bottomMargin = Utils.dp2px(12);
        params.leftMargin = Utils.dp2px(12);
        params.topMargin = Utils.dp2px(12);
        for (int i = 0; i < 50; i++) {
            tagLayout.addView(new RandTextView(this), params);
        }

        tagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // startActivity(new Intent(ActivityA.this, ActivityB.class));
                startActivity(new Intent(ActivityA.this, ActivityB.class),
                        ActivityOptions.makeSceneTransitionAnimation(ActivityA.this).toBundle());

            }
        });

    }


    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.BOTTOM);
        slideTransition.setDuration(1000);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }
}
